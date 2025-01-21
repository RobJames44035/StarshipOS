/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package java.lang.classfile.instruction;

import java.lang.classfile.CodeBuilder;
import java.lang.classfile.CodeElement;
import java.lang.classfile.CodeModel;
import java.lang.classfile.Instruction;
import java.lang.classfile.Opcode;
import java.lang.classfile.TypeKind;
import java.lang.classfile.constantpool.ClassEntry;
import java.lang.classfile.constantpool.InterfaceMethodRefEntry;
import java.lang.classfile.constantpool.MemberRefEntry;
import java.lang.classfile.constantpool.MethodRefEntry;
import java.lang.classfile.constantpool.NameAndTypeEntry;
import java.lang.classfile.constantpool.Utf8Entry;
import java.lang.constant.MethodTypeDesc;

import jdk.internal.classfile.impl.AbstractInstruction;
import jdk.internal.classfile.impl.TemporaryConstantPool;
import jdk.internal.classfile.impl.Util;

/**
 * Models a method invocation instruction in the {@code code} array of a {@code
 * Code} attribute, other than {@link InvokeDynamicInstruction invokedynamic}.
 * Corresponding opcodes have a {@linkplain Opcode#kind() kind} of {@link Opcode.Kind#INVOKE}.
 * Delivered as a {@link CodeElement} when traversing the elements of a {@link CodeModel}.
 * <p>
 * A method invocation instruction is composite:
 * {@snippet lang=text :
 * // @link substring="InvokeInstruction" target="#of(Opcode, MemberRefEntry)" :
 * InvokeInstruction(
 *     Opcode opcode, // @link substring="opcode" target="#opcode()"
 *     MethodRefEntry | InterfaceMethodRefEntry method) // @link substring="method" target="#method()"
 * )
 * }
 * where {@code method} must be an {@code InterfaceMethodRefEntry} for {@link
 * Opcode#INVOKEINTERFACE invokeinterface} opcode, and must be a {@code
 * MethodRefEntry} for {@link Opcode#INVOKEVIRTUAL invokevirtual} opcode.
 * {@link Opcode#INVOKESTATIC invokestatic} and {@link Opcode#INVOKESPECIAL
 * invokespecial} can have either type of entry for {@code method}.
 *
 * @see Opcode.Kind#INVOKE
 * @see CodeBuilder#invoke CodeBuilder::invoke
 * @since 24
 */
public sealed interface InvokeInstruction extends Instruction
        permits AbstractInstruction.BoundInvokeInterfaceInstruction, AbstractInstruction.BoundInvokeInstruction, AbstractInstruction.UnboundInvokeInstruction {
    /**
     * {@return the {@link MethodRefEntry} or {@link InterfaceMethodRefEntry}
     * constant described by this instruction}
     */
    MemberRefEntry method();

    /**
     * {@return whether the class or interface holding the method is an interface}
     */
    boolean isInterface();

    /**
     * {@return the {@code count} value of an {@code invokeinterface} instruction,
     * or {@code 0} for other instructions}
     * <p>
     * For an {@code invokeinterface} instruction, this value must be equivalent
     * to the sum of {@linkplain TypeKind#slotSize() slot sizes} of all arguments
     * plus one, which is equal to the number of operand stack depth consumed by
     * this interface method invocation instruction.
     *
     * @jvms 6.5.invokeinterface <em>invokeinterface</em>
     */
    int count();

    /**
     * {@return the class or interface holding the method}
     */
    default ClassEntry owner() {
        return method().owner();
    }

    /**
     * {@return the name of the method}
     */
    default Utf8Entry name() {
        return method().nameAndType().name();
    }

    /**
     * {@return the method descriptor string of the method}
     *
     * @apiNote
     * A symbolic descriptor for the type of the method is available through
     * {@link #typeSymbol() typeSymbol()}.
     */
    default Utf8Entry type() {
        return method().nameAndType().type();
    }

    /**
     * {@return a symbolic descriptor for the method type}
     */
    default MethodTypeDesc typeSymbol() {
        return Util.methodTypeSymbol(method().type());
    }

    /**
     * {@return an invocation instruction}
     *
     * @param op the opcode for the specific type of invocation instruction,
     *           which must be of kind {@link Opcode.Kind#INVOKE}
     * @param method a constant pool entry describing the method
     * @throws IllegalArgumentException if the opcode kind is not
     *         {@link Opcode.Kind#INVOKE}
     */
    static InvokeInstruction of(Opcode op, MemberRefEntry method) {
        Util.checkKind(op, Opcode.Kind.INVOKE);
        return new AbstractInstruction.UnboundInvokeInstruction(op, method);
    }

    /**
     * {@return an invocation instruction}
     *
     * @param op the opcode for the specific type of invocation instruction,
     *           which must be of kind {@link Opcode.Kind#INVOKE}
     * @param owner the class holding the method
     * @param name the name of the method
     * @param type the method descriptor
     * @param isInterface whether the class holding the method is an interface
     * @throws IllegalArgumentException if the opcode kind is not
     *         {@link Opcode.Kind#INVOKE}
     */
    static InvokeInstruction of(Opcode op,
                                ClassEntry owner,
                                Utf8Entry name,
                                Utf8Entry type,
                                boolean isInterface) {
        return of(op, owner, TemporaryConstantPool.INSTANCE.nameAndTypeEntry(name, type), isInterface);
    }

    /**
     * {@return an invocation instruction}
     *
     * @param op the opcode for the specific type of invocation instruction,
     *           which must be of kind {@link Opcode.Kind#INVOKE}
     * @param owner the class holding the method
     * @param nameAndType the name and type of the method
     * @param isInterface whether the class holding the method is an interface
     * @throws IllegalArgumentException if the opcode kind is not
     *         {@link Opcode.Kind#INVOKE}
     */
    static InvokeInstruction of(Opcode op,
                                ClassEntry owner,
                                NameAndTypeEntry nameAndType,
                                boolean isInterface) {
        return of(op, isInterface
                      ? TemporaryConstantPool.INSTANCE.interfaceMethodRefEntry(owner, nameAndType)
                      : TemporaryConstantPool.INSTANCE.methodRefEntry(owner, nameAndType));
    }
}
