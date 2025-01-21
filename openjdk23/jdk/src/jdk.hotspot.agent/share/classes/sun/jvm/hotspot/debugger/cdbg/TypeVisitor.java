/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.jvm.hotspot.debugger.cdbg;

public interface TypeVisitor {
  public void doBitType(BitType t);
  public void doIntType(IntType t);
  public void doEnumType(EnumType t);
  public void doFloatType(FloatType t);
  public void doDoubleType(DoubleType t);
  public void doPointerType(PointerType t);
  public void doArrayType(ArrayType t);
  public void doRefType(RefType t);
  public void doCompoundType(CompoundType t);
  public void doFunctionType(FunctionType t);
  public void doMemberFunctionType(MemberFunctionType t);
  public void doVoidType(VoidType t);
}
