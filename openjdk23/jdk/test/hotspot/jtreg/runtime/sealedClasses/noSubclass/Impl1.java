/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package noSubclass;

final class ImplC extends BaseC {}
non-sealed class ImplCIntermediate extends BaseC {}
final class ImplCIndirect extends BaseC {}

non-sealed interface ImplII extends BaseI {}
non-sealed interface ImplIIntermediateI extends BaseI {}
non-sealed interface ImplIIndirectI extends ImplIIntermediateI, BaseI {}

final class ImplIC implements BaseI {}
non-sealed class ImplIIntermediateC implements BaseI {}
final class ImplIIndirectC extends ImplIIntermediateC implements BaseI {}
