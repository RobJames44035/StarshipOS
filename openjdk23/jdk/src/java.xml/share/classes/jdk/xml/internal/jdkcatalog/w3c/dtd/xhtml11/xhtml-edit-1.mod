<!--
  - StarshipOS Copyright (c) 2025. R.A. James
  -->

<!-- ...................................................................... -->
<!-- XHTML Editing Elements Module  ....................................... -->
<!-- Editing Elements

        ins, del

     This module declares element types and attributes used to indicate
     inserted and deleted content while editing a document.
-->

<!-- ins: Inserted Text  ............................... -->

<!ENTITY % ins.element  "INCLUDE" >
<![%ins.element;[
<!ENTITY % ins.content
     "( #PCDATA | %Flow.mix; )*"
>
<!ENTITY % ins.qname  "ins" >
<!ELEMENT %ins.qname;  %ins.content; >
<!-- end of ins.element -->]]>

<!ENTITY % ins.attlist  "INCLUDE" >
<![%ins.attlist;[
<!ATTLIST %ins.qname;
      %Common.attrib;
      cite         %URI.datatype;           #IMPLIED
      datetime     %Datetime.datatype;      #IMPLIED
>
<!-- end of ins.attlist -->]]>

<!-- del: Deleted Text  ................................ -->

<!ENTITY % del.element  "INCLUDE" >
<![%del.element;[
<!ENTITY % del.content
     "( #PCDATA | %Flow.mix; )*"
>
<!ENTITY % del.qname  "del" >
<!ELEMENT %del.qname;  %del.content; >
<!-- end of del.element -->]]>

<!ENTITY % del.attlist  "INCLUDE" >
<![%del.attlist;[
<!ATTLIST %del.qname;
      %Common.attrib;
      cite         %URI.datatype;           #IMPLIED
      datetime     %Datetime.datatype;      #IMPLIED
>
<!-- end of del.attlist -->]]>

<!-- end of xhtml-edit-1.mod -->
