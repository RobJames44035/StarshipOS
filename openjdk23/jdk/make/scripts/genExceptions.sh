#! /bin/sh
#
# StarshipOS Copyright (c) 2000-2025. R.A. James
#

# Generate exception classes

SPEC=$1
DST=$2

gen() {
  ID=$1
  WHAT=$2
  SVUID=$3
  ARG_TYPE=$4
  ARG_ID=$5
  ARG_PROP=$6
  ARG_PHRASE=$7
  ARG_PARAM="$ARG_TYPE$ $ARG_ID"
  echo '-->' $DST/$ID.java
  out=$DST/${ID}.java

  $SH ${SCRIPTS}/addNotices.sh "$COPYRIGHT_YEARS" > $out

  cat >>$out <<__END__

// -- This file was mechanically generated: Do not edit! -- //

package $PACKAGE;


/**$WHAT
 *
 * @since $SINCE
 */

public `if [ ${ABSTRACT:-0} = 1 ];
        then echo 'abstract '; fi`class $ID
    extends ${SUPER}
{

    @java.io.Serial
    private static final long serialVersionUID = $SVUID;
__END__

  if [ $ARG_ID ]; then

    cat >>$out <<__END__

    /**
     * The $ARG_PHRASE.
     */
    private $ARG_TYPE $ARG_ID;

    /**
     * Constructs an instance of this class.
     *
     * @param  $ARG_ID
     *         The $ARG_PHRASE
     */
    public $ID($ARG_TYPE $ARG_ID) {
        super(String.valueOf($ARG_ID));
	this.$ARG_ID = $ARG_ID;
    }

    /**
     * Retrieves the $ARG_PHRASE.
     *
     * @return  The $ARG_PHRASE
     */
    public $ARG_TYPE get$ARG_PROP() {
        return $ARG_ID;
    }

}
__END__

  else

    cat >>$out <<__END__

    /**
     * Constructs an instance of this class.
     */
    public $ID() { }

}
__END__

  fi
}

. $SPEC
