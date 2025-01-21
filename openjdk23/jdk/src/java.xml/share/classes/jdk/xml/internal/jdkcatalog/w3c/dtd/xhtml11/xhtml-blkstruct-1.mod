<!--
  - StarshipOS Copyright (c) 2025. R.A. James
  -->

<!-- ...................................................................... -->
<!-- XHTML Block Structural Module  ....................................... -->
<!-- Block Structural

        div, p

     This module declares the elements and their attributes used to
     support block-level structural markup.
-->

<!ENTITY % div.element  "INCLUDE" >
<![%div.element;[
<!ENTITY % div.content
     "( #PCDATA | %Flow.mix; )*"
>
<!ENTITY % div.qname  "div" >
<!ELEMENT %div.qname;  %div.content; >
<!-- end of div.element -->]]>

<!ENTITY % div.attlist  "INCLUDE" >
<![%div.attlist;[
<!ATTLIST %div.qname;
      %Common.attrib;
>
<!-- end of div.attlist -->]]>

<!ENTITY % p.element  "INCLUDE" >
<![%p.element;[
<!ENTITY % p.content
     "( #PCDATA | %Inline.mix; )*" >
<!ENTITY % p.qname  "p" >
<!ELEMENT %p.qname;  %p.content; >
<!-- end of p.element -->]]>

<!ENTITY % p.attlist  "INCLUDE" >
<![%p.attlist;[
<!ATTLIST %p.qname;
      %Common.attrib;
>
<!-- end of p.attlist -->]]>

<!-- end of xhtml-blkstruct-1.mod -->
