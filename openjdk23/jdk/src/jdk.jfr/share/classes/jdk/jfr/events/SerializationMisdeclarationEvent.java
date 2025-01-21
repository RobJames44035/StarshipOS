/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.jfr.events;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.internal.MirrorEvent;
import jdk.jfr.internal.RemoveFields;
import jdk.jfr.internal.Type;

@Name(Type.EVENT_NAME_PREFIX + "SerializationMisdeclaration")
@Label("Serialization Misdeclaration")
@Category({"Java Development Kit", "Serialization"})
@Description("Methods and fields misdeclarations." +
        " The checks are usually performed just once per serializable class," +
        " the first time it is used by serialization." +
        " Under high memory pressure, a class might be re-checked again.")
@RemoveFields({"duration", "stackTrace", "eventThread"})
public final class SerializationMisdeclarationEvent extends MirrorEvent {

    @Label("Misdeclared Class")
    public Class<?> misdeclaredClass;

    @Label("Message")
    public String message;

}
