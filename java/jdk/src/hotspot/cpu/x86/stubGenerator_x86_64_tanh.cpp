/*
* Copyright (c) 2024, Intel Corporation. All rights reserved.
* Intel Math Library (LIBM) Source Code
*
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
*
* This code is free software; you can redistribute it and/or modify it
* under the terms of the GNU General Public License version 2 only, as
* published by the Free Software Foundation.
*
* This code is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
* FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
* version 2 for more details (a copy is included in the LICENSE file that
* accompanied this code).
*
* You should have received a copy of the GNU General Public License version
* 2 along with this work; if not, write to the Free Software Foundation,
* Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
*
* Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
* or visit www.oracle.com if you need additional information or have any
* questions.
*
*/

#include "precompiled.hpp"
#include "macroAssembler_x86.hpp"
#include "stubGenerator_x86_64.hpp"

/******************************************************************************/
//                     ALGORITHM DESCRIPTION
//                     ---------------------
//
// tanh(x)=(exp(x)-exp(-x))/(exp(x)+exp(-x))=(1-exp(-2*x))/(1+exp(-2*x))
//
// Let |x|=xH+xL (upper 26 bits, lower 27 bits)
// log2(e) rounded to 26 bits (high part) plus a double precision low part is
//         L2EH+L2EL (upper 26, lower 53 bits)
//
// Let xH*L2EH=k+f+r`, where (k+f)*2^8*2=int(xH*L2EH*2^9),
//                             f=0.b1 b2 ... b8, k integer
// 2^{-f} is approximated as Tn[f]+Dn[f]
// Tn stores the high 53 bits, Dn stores (2^{-f}-Tn[f]) rounded to double precision
//
//  r=r`+xL*L2EH+|x|*L2EL, |r|<2^{-9}+2^{-14},
//                      for |x| in [23/64,3*2^7)
// e^{-2*|x|}=2^{-k-f}*2^{-r} ~ 2^{-k}*(Tn+Dn)*(1+p)=(T0+D0)*(1+p)
//
// For |x| in [2^{-4},2^5):
//         2^{-r}-1 ~ p=c1*r+c2*r^2+..+c5*r^5
//      Let R=1/(1+T0+p*T0), truncated to 35 significant bits
//  R=1/(1+T0+D0+p*(T0+D0))*(1+eps), |eps|<2^{-33}
//  1+T0+D0+p*(T0+D0)=KH+KL, where
//       KH=(1+T0+c1*r*T0)_high (leading 17 bits)
//       KL=T0_low+D0+(c1*r*T0)_low+c1*r*D0+(c2*r^2+..c5*r^5)*T0
//  eps ~ (R*KH-1)+R*KL
//  1/(1+T0+D0+p*(T0+D0)) ~ R-R*eps
//  The result is approximated as (1-T0-D0-(T0+D0)*p)*(R-R*eps)
//  1-T0-D0-(T0+D0)*p=-((KH-2)+KL)
//    The result is formed as
//    (KH-2)*R+(-(KH-2)*R*eps+(KL*R-KL*R*eps)), with the correct sign
//                                                  set at the end
//
// For |x| in [2^{-64},2^{-4}):
//  A Taylor series expansion is used  (x+p3*x^3+..+p13*x^{13})
//
// For |x|<2^{-64}:  x is returned
//
// For |x|>=2^32: return +/-1
//
// Special cases:
//  tanh(NaN) = quiet NaN, and raise invalid exception
//  tanh(INF) = that INF
//  tanh(+/-0) = +/-0
//
/******************************************************************************/

ATTRIBUTE_ALIGNED(4) static const juint _HALFMASK[] =
{
    4160749568, 2147483647
};

ATTRIBUTE_ALIGNED(4) static const juint _ONEMASK[] =
{
    0, 1072693248
};

ATTRIBUTE_ALIGNED(4) static const juint _TWOMASK[] =
{
    0, 1073741824
};

ATTRIBUTE_ALIGNED(16) static const juint _MASK3[] =
{
    0, 4294967280, 0, 4294967280
};

ATTRIBUTE_ALIGNED(16) static const juint _RMASK[] =
{
    4294705152, 4294967295, 4294705152, 4294967295
};

ATTRIBUTE_ALIGNED(16) static const juint _L2E[] =
{
    1610612736, 1082594631, 4166901572, 1055174155
};

ATTRIBUTE_ALIGNED(16) static const juint _Shifter[] =
{
    0, 1127743488, 0, 3275227136
};

ATTRIBUTE_ALIGNED(16) static const juint _cv[] =
{
    3884607281, 3168131199, 3607404735, 3190582024, 1874480759,
    1032041131, 4286760334, 1053736893, 4277811695, 3211144770,
    0,          0
};

ATTRIBUTE_ALIGNED(4) static const juint _pv[] =
{
    236289503,  1064135997, 463583772,  3215696314, 1441186365,
    3212977891, 286331153,  1069617425, 2284589306, 1066820852,
    1431655765, 3218429269
};

ATTRIBUTE_ALIGNED(16) static const juint _T2_neg_f[] =
{
    0,          1072693248, 0,          0,          1797923801, 1072687577,
    1950547427, 1013229059, 730821105,  1072681922, 2523232743, 1012067188,
    915592468,  1072676282, 352947894,  3161024371, 2174652632, 1072670657,
    4087714590, 1014450259, 35929225,   1072665048, 2809788041, 3159436968,
    2912730644, 1072659453, 3490067722, 3163405074, 2038973688, 1072653874,
    892941374,  1016046459, 1533953344, 1072648310, 769171851,  1015665633,
    1222472308, 1072642761, 1054357470, 3161021018, 929806999,  1072637227,
    3205336643, 1015259557, 481706282,  1072631708, 1696079173, 3162710528,
    3999357479, 1072626203, 2258941616, 1015924724, 2719515920, 1072620714,
    2760332941, 1015137933, 764307441,  1072615240, 3021057420, 3163329523,
    2256325230, 1072609780, 580117746,  1015317295, 2728693978, 1072604335,
    396109971,  3163462691, 2009970496, 1072598905, 2159039665, 3162572948,
    4224142467, 1072593489, 3389820386, 1015207202, 610758006,  1072588089,
    1965209397, 3161866232, 3884662774, 1072582702, 2158611599, 1014210185,
    991358482,  1072577331, 838715019,  3163157668, 351641897,  1072571974,
    2172261526, 3163010599, 1796832535, 1072566631, 3176955716, 3160585513,
    863738719,  1072561303, 1326992220, 3162613197, 1679558232, 1072555989,
    2390342287, 3163333970, 4076975200, 1072550689, 2029000899, 1015208535,
    3594158869, 1072545404, 2456521700, 3163256561, 64696965,   1072540134,
    1768797490, 1015816960, 1912561781, 1072534877, 3147495102, 1015678253,
    382305176,  1072529635, 2347622376, 3162578625, 3898795731, 1072524406,
    1249994144, 1011869818, 3707479175, 1072519192, 3613079303, 1014164738,
    3939148246, 1072513992, 3210352148, 1015274323, 135105010,  1072508807,
    1906148728, 3163375739, 721996136,  1072503635, 563754734,  1015371318,
    1242007932, 1072498477, 1132034716, 3163339831, 1532734324, 1072493333,
    3094216535, 3163162857, 1432208378, 1072488203, 1401068914, 3162363963,
    778901109,  1072483087, 2248183955, 3161268751, 3706687593, 1072477984,
    3521726940, 1013253067, 1464976603, 1072472896, 3507292405, 3161977534,
    2483480501, 1072467821, 1216371780, 1013034172, 2307442995, 1072462760,
    3190117721, 3162404539, 777507147,  1072457713, 4282924205, 1015187533,
    2029714210, 1072452679, 613660079,  1015099143, 1610600570, 1072447659,
    3766732298, 1015760183, 3657065772, 1072442652, 399025623,  3162957078,
    3716502172, 1072437659, 2303740125, 1014042725, 1631695677, 1072432680,
    2717633076, 3162344026, 1540824585, 1072427714, 1064017011, 3163487690,
    3287523847, 1072422761, 1625971539, 3157009955, 2420883922, 1072417822,
    2049810052, 1014119888, 3080351519, 1072412896, 3379126788, 3157218001,
    815859274,  1072407984, 240396590,  3163487443, 4062661092, 1072403084,
    1422616006, 3163255318, 4076559943, 1072398198, 2119478331, 3160758351,
    703710506,  1072393326, 1384660846, 1015195891, 2380618042, 1072388466,
    3149557219, 3163320799, 364333489,  1072383620, 3923737744, 3161421373,
    3092190715, 1072378786, 814012168,  3159523422, 1822067026, 1072373966,
    1241994956, 1015340290, 697153126,  1072369159, 1283515429, 3163283189,
    3861050111, 1072364364, 254893773,  3162813180, 2572866477, 1072359583,
    878562433,  1015521741, 977020788,  1072354815, 3065100517, 1015541563,
    3218338682, 1072350059, 3404164304, 3162477108, 557149882,  1072345317,
    3672720709, 1014537265, 1434058175, 1072340587, 251133233,  1015085769,
    1405169241, 1072335870, 2998539689, 3162830951, 321958744,  1072331166,
    3401933767, 1015794558, 2331271250, 1072326474, 812057446,  1012207446,
    2990417245, 1072321795, 3683467745, 3163369326, 2152073944, 1072317129,
    1486860576, 3163203456, 3964284211, 1072312475, 2111583915, 1015427164,
    3985553595, 1072307834, 4002146062, 1015834136, 2069751141, 1072303206,
    1562170675, 3162724681, 2366108318, 1072298590, 2867985102, 3161762254,
    434316067,  1072293987, 2028358766, 1013458122, 424392917,  1072289396,
    2749202995, 3162838718, 2191782032, 1072284817, 2960257726, 1013742662,
    1297350157, 1072280251, 1308022040, 3163412558, 1892288442, 1072275697,
    2446255666, 3162600381, 3833209506, 1072271155, 2722920684, 1013754842,
    2682146384, 1072266626, 2082178513, 3163363419, 2591453363, 1072262109,
    2132396182, 3159074198, 3418903055, 1072257604, 2527457337, 3160820604,
    727685349,  1072253112, 2038246809, 3162358742, 2966275557, 1072248631,
    2176155324, 3159842759, 1403662306, 1072244163, 2788809599, 3161671007,
    194117574,  1072239707, 777528612,  3163412089, 3492293770, 1072235262,
    2248032210, 1015386826, 2568320822, 1072230830, 2732824428, 1014352915,
    1577608921, 1072226410, 1875489510, 3162968394, 380978316,  1072222002,
    854188970,  3160462686, 3134592888, 1072217605, 4232266862, 1015991134,
    1110089947, 1072213221, 1451641639, 1015474673, 2759350287, 1072208848,
    1148526634, 1015894933, 3649726105, 1072204487, 4085036346, 1015649474,
    3643909174, 1072200138, 3537586109, 1014354647, 2604962541, 1072195801,
    2614425274, 3163539192, 396319521,  1072191476, 4172420816, 3159074632,
    1176749997, 1072187162, 2738998779, 3162035844, 515457527,  1072182860,
    836709333,  1015651226, 2571947539, 1072178569, 3558159064, 3163376669,
    2916157145, 1072174290, 219487565,  1015309367, 1413356050, 1072170023,
    1651349291, 3162668166, 2224145553, 1072165767, 3482522030, 3161489169,
    919555682,  1072161523, 3121969534, 1012948226, 1660913392, 1072157290,
    4218599604, 1015135707, 19972402,   1072153069, 3507899862, 1016009292,
    158781403,  1072148859, 2221464712, 3163286453, 1944781191, 1072144660,
    3993278767, 3161724279, 950803702,  1072140473, 1655364926, 1015237032,
    1339972927, 1072136297, 167908909,  1015572152, 2980802057, 1072132132,
    378619896,  1015773303, 1447192521, 1072127979, 1462857171, 3162514521,
    903334909,  1072123837, 1636462108, 1015039997, 1218806132, 1072119706,
    1818613052, 3162548441, 2263535754, 1072115586, 752233586,  3162639008,
    3907805044, 1072111477, 2257091225, 3161550407, 1727278727, 1072107380,
    3562710623, 1011471940, 4182873220, 1072103293, 629542646,  3161996303,
    2555984613, 1072099218, 2652555442, 3162552692, 1013258799, 1072095154,
    1748797611, 3160129082, 3721688645, 1072091100, 3069276937, 1015839401,
    1963711167, 1072087058, 1744767757, 3160574294, 4201977662, 1072083026,
    748330254,  1013594357, 1719614413, 1072079006, 330458198,  3163282740,
    2979960120, 1072074996, 2599109725, 1014498493, 3561793907, 1072070997,
    1157054053, 1011890350, 3339203574, 1072067009, 1483497780, 3162408754,
    2186617381, 1072063032, 2270764084, 3163272713, 4273770423, 1072059065,
    3383180809, 3163218901, 885834528,  1072055110, 1973258547, 3162261564,
    488188413,  1072051165, 3199821029, 1015564048, 2956612997, 1072047230,
    2118169751, 3162735553, 3872257780, 1072043306, 1253592103, 1015958334,
    3111574537, 1072039393, 2606161479, 3162759746, 551349105,  1072035491,
    3821916050, 3162106589, 363667784,  1072031599, 813753950,  1015785209,
    2425981843, 1072027717, 2830390851, 3163346599, 2321106615, 1072023846,
    2171176610, 1009535771, 4222122499, 1072019985, 1277378074, 3163256737,
    3712504873, 1072016135, 88491949,   1015427660, 671025100,  1072012296,
    3832014351, 3163022030, 3566716925, 1072008466, 1536826856, 1014142433,
    3689071823, 1072004647, 2321004996, 3162552716, 917841882,  1072000839,
    18715565,   1015659308, 3723038930, 1071997040, 378465264,  3162569582,
    3395129871, 1071993252, 4025345435, 3162335388, 4109806887, 1071989474,
    422403966,  1014469229, 1453150082, 1071985707, 498154669,  3161488062,
    3896463087, 1071981949, 1139797873, 3161233805, 2731501122, 1071978202,
    1774031855, 3162470021, 2135241198, 1071974465, 1236747871, 1013589147,
    1990012071, 1071970738, 3529070563, 3162813193, 2178460671, 1071967021,
    777878098,  3162842493, 2583551245, 1071963314, 3161094195, 1015606491,
    3088564500, 1071959617, 1762311517, 1015045673, 3577096743, 1071955930,
    2951496418, 1013793687, 3933059031, 1071952253, 2133366768, 3161531832,
    4040676318, 1071948586, 4090609238, 1015663458, 3784486610, 1071944929,
    1581883040, 3161698953, 3049340112, 1071941282, 3062915824, 1013170595,
    1720398391, 1071937645, 3980678963, 3163300080, 3978100823, 1071934017,
    3513027190, 1015845963, 1118294578, 1071930400, 2197495694, 3159909401,
    1617004845, 1071926792, 82804944,   1010342778, 1065662932, 1071923194,
    2533670915, 1014530238, 3645941911, 1071919605, 3814685081, 3161573341,
    654919306,  1071916027, 3232961757, 3163047469, 569847338,  1071912458,
    472945272,  3159290729, 3278348324, 1071908898, 3069497416, 1014750712,
    78413852,   1071905349, 4183226867, 3163017251, 3743175029, 1071901808,
    2072812490, 3162175075, 1276261410, 1071898278, 300981948,  1014684169,
    1156440435, 1071894757, 2351451249, 1013967056, 3272845541, 1071891245,
    928852419,  3163488248, 3219942644, 1071887743, 3798990616, 1015368806,
    887463927,  1071884251, 3596744163, 3160794166, 460407023,  1071880768,
    4237175092, 3163138469, 1829099622, 1071877294, 1016661181, 3163461005,
    589198666,  1071873830, 2664346172, 3163157962, 926591435,  1071870375,
    3208833762, 3162913514, 2732492859, 1071866929, 2691479646, 3162255684,
    1603444721, 1071863493, 1548633640, 3162201326, 1726216749, 1071860066,
    2466808228, 3161676405, 2992903935, 1071856648, 2218154406, 1015228193,
    1000925746, 1071853240, 1018491672, 3163309544, 4232894513, 1071849840,
    2383938684, 1014668519, 3991843581, 1071846450, 4092853457, 1014585763,
    171030293,  1071843070, 3526460132, 1014428778, 1253935211, 1071839698,
    1395382931, 3159702613, 2839424854, 1071836335, 1171596163, 1013041679,
    526652809,  1071832982, 4223459736, 1015879375, 2799960843, 1071829637,
    1423655381, 1015022151, 964107055,  1071826302, 2800439588, 3162833221,
    3504003472, 1071822975, 3594001060, 3157330652, 1724976915, 1071819658,
    420909223,  3163117379, 4112506593, 1071816349, 2947355221, 1014371048,
    1972484976, 1071813050, 675290301,  3161640050, 3790955393, 1071809759,
    2352942462, 3163180090, 874372905,  1071806478, 100263788,  1015940732,
    1709341917, 1071803205, 2571168217, 1014152499, 1897844341, 1071799941,
    1254300460, 1015275938, 1337108031, 1071796686, 3203724452, 1014677845,
    4219606026, 1071793439, 2434574742, 1014681548, 1853186616, 1071790202,
    3066496371, 1015656574, 2725843665, 1071786973, 1433917087, 1014838523,
    2440944790, 1071783753, 2492769774, 1014147454, 897099801,  1071780542,
    754756297,  1015241005, 2288159958, 1071777339, 2169144469, 1014876021,
    2218315341, 1071774145, 2694295388, 3163288868, 586995997,  1071770960,
    41662348,   3162627992, 1588871207, 1071767783, 143439582,  3162963416,
    828946858,  1071764615, 10642492,   1015939438, 2502433899, 1071761455,
    2148595913, 1015023991, 2214878420, 1071758304, 892270087,  3163116422,
    4162030108, 1071755161, 2763428480, 1015529349, 3949972341, 1071752027,
    2068408548, 1014913868, 1480023343, 1071748902, 2247196168, 1015327453,
    948735466,  1071745785, 3516338028, 3162574883, 2257959872, 1071742676,
    3802946148, 1012964927, 1014845819, 1071739576, 3117910646, 3161559105,
    1416741826, 1071736484, 2196380210, 1011413563, 3366293073, 1071733400,
    3119426314, 1014120554, 2471440686, 1071730325, 968836267,  3162214888,
    2930322912, 1071727258, 2599499422, 3162714047, 351405227,  1071724200,
    3125337328, 3159822479, 3228316108, 1071721149, 3010241991, 3158422804,
    2875075254, 1071718107, 4144233330, 3163333716, 3490863953, 1071715073,
    960797498,  3162948880, 685187902,  1071712048, 378731989,  1014843115,
    2952712987, 1071709030, 3293494651, 3160120301, 1608493509, 1071706021,
    3159622171, 3162807737, 852742562,  1071703020, 667253586,  1009793559,
    590962156,  1071700027, 3829346666, 3163275597, 728909815,  1071697042,
    383930225,  1015029468, 1172597893, 1071694065, 114433263,  1015347593,
    1828292879, 1071691096, 1255956747, 1015588398, 2602514713, 1071688135,
    2268929336, 1014354284, 3402036099, 1071685182, 405889334,  1015105656,
    4133881824, 1071682237, 2148155345, 3162931299, 410360776,  1071679301,
    1269990655, 1011975870, 728934454,  1071676372, 1413842688, 1014178612,
    702412510,  1071673451, 3803266087, 3162280415, 238821257,  1071670538,
    1469694871, 3162884987, 3541402996, 1071667632, 2759177317, 1014854626,
    1928746161, 1071664735, 983617676,  1014285177, 3899555717, 1071661845,
    427280750,  3162546972, 772914124,  1071658964, 4004372762, 1012230161,
    1048019041, 1071656090, 1398474845, 3160510595, 339411585,  1071653224,
    264588982,  3161636657, 2851812149, 1071650365, 2595802551, 1015767337,
    4200250559, 1071647514, 2808127345, 3161781938
};

#define __ _masm->

address StubGenerator::generate_libmTanh() {
  StubCodeMark mark(this, "StubRoutines", "libmTanh");
  address start = __ pc();

  Label L_2TAG_PACKET_0_0_1, L_2TAG_PACKET_1_0_1, L_2TAG_PACKET_2_0_1, L_2TAG_PACKET_3_0_1;
  Label L_2TAG_PACKET_4_0_1, L_2TAG_PACKET_5_0_1;
  Label B1_2, B1_4;

  address HALFMASK     = (address)_HALFMASK;
  address ONEMASK      = (address)_ONEMASK;
  address TWOMASK      = (address)_TWOMASK;
  address MASK3        = (address)_MASK3;
  address RMASK        = (address)_RMASK;
  address L2E          = (address)_L2E;
  address Shifter      = (address)_Shifter;
  address cv           = (address)_cv;
  address pv           = (address)_pv;
  address T2_neg_f     = (address) _T2_neg_f;

  __ enter(); // required for proper stackwalking of RuntimeStub frame

  __ bind(B1_2);
  __ movsd(xmm3, ExternalAddress(HALFMASK), r11 /*rscratch*/);
  __ xorpd(xmm4, xmm4);
  __ movsd(xmm1, ExternalAddress(L2E), r11 /*rscratch*/);
  __ movsd(xmm2, ExternalAddress(L2E + 8), r11 /*rscratch*/);
  __ movl(rax, 32768);
  __ pinsrw(xmm4, rax, 3);
  __ movsd(xmm6,  ExternalAddress(Shifter), r11 /*rscratch*/);
  __ pextrw(rcx, xmm0, 3);
  __ andpd(xmm3, xmm0);
  __ andnpd(xmm4, xmm0);
  __ pshufd(xmm5, xmm4, 68);
  __ movl(rdx, 32768);
  __ andl(rdx, rcx);
  __ andl(rcx, 32767);
  __ subl(rcx, 16304);
  __ cmpl(rcx, 144);
  __ jcc(Assembler::aboveEqual, L_2TAG_PACKET_0_0_1);
  __ subsd(xmm4, xmm3);
  __ mulsd(xmm3, xmm1);
  __ mulsd(xmm2, xmm5);
  __ cvtsd2siq(rax, xmm3);
  __ movq(xmm7, xmm3);
  __ addsd(xmm3, xmm6);
  __ mulsd(xmm1, xmm4);
  __ movsd(xmm4, ExternalAddress(ONEMASK), r11 /*rscratch*/);
  __ subsd(xmm3, xmm6);
  __ xorpd(xmm0, xmm0);
  __ addsd(xmm2, xmm1);
  __ subsd(xmm7, xmm3);
  __ movdqu(xmm6, ExternalAddress(cv), r11 /*rscratch*/);
  __ addsd(xmm2, xmm7);
  __ movl(rcx, 255);
  __ andl(rcx, rax);
  __ addl(rcx, rcx);
  __ lea(r8, ExternalAddress(T2_neg_f));
  __ movdqu(xmm5, Address(r8, rcx, Address::times(8)));
  __ shrl(rax, 4);
  __ andl(rax, 65520);
  __ subl(rax, 16368);
  __ negl(rax);
  __ pinsrw(xmm0, rax, 3);
  __ movdqu(xmm1, ExternalAddress(cv + 16), r11 /*rscratch*/);
  __ pshufd(xmm0, xmm0, 68);
  __ mulpd(xmm0, xmm5);
  __ movsd(xmm7, ExternalAddress(cv + 32), r11 /*rscratch*/);
  __ pshufd(xmm2, xmm2, 68);
  __ movq(xmm5, xmm4);
  __ addsd(xmm4, xmm0);
  __ mulpd(xmm6, xmm2);
  __ mulsd(xmm7, xmm2);
  __ mulpd(xmm2, xmm2);
  __ addpd(xmm1, xmm6);
  __ mulsd(xmm2, xmm2);
  __ movsd(xmm3, ExternalAddress(ONEMASK), r11 /*rscratch*/);
  __ mulpd(xmm1, xmm2);
  __ pshufd(xmm6, xmm1, 78);
  __ addsd(xmm1, xmm6);
  __ movq(xmm6, xmm1);
  __ addsd(xmm1, xmm7);
  __ mulsd(xmm1, xmm0);
  __ addsd(xmm1, xmm4);
  __ andpd(xmm4, ExternalAddress(MASK3), r11 /*rscratch*/);
  __ divsd(xmm5, xmm1);
  __ subsd(xmm3, xmm4);
  __ pshufd(xmm1, xmm0, 238);
  __ addsd(xmm3, xmm0);
  __ movq(xmm2, xmm4);
  __ addsd(xmm3, xmm1);
  __ mulsd(xmm1, xmm7);
  __ mulsd(xmm7, xmm0);
  __ addsd(xmm3, xmm1);
  __ addsd(xmm4, xmm7);
  __ movsd(xmm1, ExternalAddress(RMASK), r11 /*rscratch*/);
  __ mulsd(xmm6, xmm0);
  __ andpd(xmm4, ExternalAddress(MASK3), r11 /*rscratch*/);
  __ addsd(xmm3, xmm6);
  __ movq(xmm6, xmm4);
  __ subsd(xmm2, xmm4);
  __ addsd(xmm2, xmm7);
  __ movsd(xmm7, ExternalAddress(ONEMASK), r11 /*rscratch*/);
  __ andpd(xmm5, xmm1);
  __ addsd(xmm3, xmm2);
  __ mulsd(xmm4, xmm5);
  __ xorpd(xmm2, xmm2);
  __ mulsd(xmm3, xmm5);
  __ subsd(xmm6, ExternalAddress(TWOMASK), r11 /*rscratch*/);
  __ subsd(xmm4, xmm7);
  __ xorl(rdx, 32768);
  __ pinsrw(xmm2, rdx, 3);
  __ addsd(xmm4, xmm3);
  __ mulsd(xmm6, xmm5);
  __ movq(xmm1, xmm3);
  __ mulsd(xmm3, xmm4);
  __ movq(xmm0, xmm6);
  __ mulsd(xmm6, xmm4);
  __ subsd(xmm1, xmm3);
  __ subsd(xmm1, xmm6);
  __ addsd(xmm0, xmm1);
  __ xorpd(xmm0, xmm2);
  __ jmp(B1_4);

  __ bind(L_2TAG_PACKET_0_0_1);
  __ addl(rcx, 960);
  __ cmpl(rcx, 1104);
  __ jcc(Assembler::aboveEqual, L_2TAG_PACKET_1_0_1);
  __ movdqu(xmm2, ExternalAddress(pv), r11 /*rscratch*/);
  __ pshufd(xmm1, xmm0, 68);
  __ movdqu(xmm3, ExternalAddress(pv + 16), r11 /*rscratch*/);
  __ mulpd(xmm1, xmm1);
  __ movdqu(xmm4, ExternalAddress(pv + 32), r11 /*rscratch*/);
  __ mulpd(xmm2, xmm1);
  __ pshufd(xmm5, xmm1, 68);
  __ addpd(xmm2, xmm3);
  __ mulsd(xmm5, xmm5);
  __ mulpd(xmm2, xmm1);
  __ mulsd(xmm5, xmm5);
  __ addpd(xmm2, xmm4);
  __ mulpd(xmm2, xmm5);
  __ pshufd(xmm5, xmm2, 238);
  __ addsd(xmm2, xmm5);
  __ mulsd(xmm2, xmm0);
  __ addsd(xmm0, xmm2);
  __ jmp(B1_4);

  __ bind(L_2TAG_PACKET_1_0_1);
  __ addl(rcx, 15344);
  __ cmpl(rcx, 16448);
  __ jcc(Assembler::aboveEqual, L_2TAG_PACKET_2_0_1);
  __ cmpl(rcx, 16);
  __ jcc(Assembler::below, L_2TAG_PACKET_3_0_1);
  __ xorpd(xmm2, xmm2);
  __ movl(rax, 17392);
  __ pinsrw(xmm2, rax, 3);
  __ mulsd(xmm2, xmm0);
  __ addsd(xmm2, xmm0);
  __ jmp(B1_4);

  __ bind(L_2TAG_PACKET_3_0_1);
  __ movq(xmm2, xmm0);
  __ mulsd(xmm2, xmm2);
  __ jmp(B1_4);

  __ bind(L_2TAG_PACKET_2_0_1);
  __ cmpl(rcx, 32752);
  __ jcc(Assembler::aboveEqual, L_2TAG_PACKET_4_0_1);
  __ xorpd(xmm2, xmm2);
  __ movl(rcx, 15344);
  __ pinsrw(xmm2, rcx, 3);
  __ movq(xmm3, xmm2);
  __ mulsd(xmm2, xmm2);
  __ addsd(xmm2, xmm3);

  __ bind(L_2TAG_PACKET_5_0_1);
  __ xorpd(xmm0, xmm0);
  __ orl(rdx, 16368);
  __ pinsrw(xmm0, rdx, 3);
  __ jmp(B1_4);

  __ bind(L_2TAG_PACKET_4_0_1);
  __ movq(xmm2, xmm0);
  __ movdl(rax, xmm0);
  __ psrlq(xmm2, 20);
  __ movdl(rcx, xmm2);
  __ orl(rcx, rax);
  __ cmpl(rcx, 0);
  __ jcc(Assembler::equal, L_2TAG_PACKET_5_0_1);
  __ addsd(xmm0, xmm0);

  __ bind(B1_4);
  __ leave(); // required for proper stackwalking of RuntimeStub frame
  __ ret(0);

  return start;
}

#undef __