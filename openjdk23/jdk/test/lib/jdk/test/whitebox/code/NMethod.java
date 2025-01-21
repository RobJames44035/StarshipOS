/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package jdk.test.whitebox.code;

import java.lang.reflect.Executable;
import jdk.test.whitebox.WhiteBox;

public class NMethod extends CodeBlob {
  private static final WhiteBox wb = WhiteBox.getWhiteBox();
  public static NMethod get(Executable method, boolean isOsr) {
    Object[] obj = wb.getNMethod(method, isOsr);
    return obj == null ? null : new NMethod(obj);
  }
  private NMethod(Object[] obj) {
    super((Object[])obj[0]);
    assert obj.length == 5;
    comp_level = (Integer) obj[1];
    insts = (byte[]) obj[2];
    compile_id = (Integer) obj[3];
    entry_point = (Long) obj[4];
  }
  public final byte[] insts;
  public final int comp_level;
  public final int compile_id;
  public final long entry_point;

  @Override
  public String toString() {
    return "NMethod{"
        + super.toString()
        + ", insts=" + insts
        + ", comp_level=" + comp_level
        + ", compile_id=" + compile_id
        + ", entry_point=" + entry_point
        + '}';
  }
}
