/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

// fail to create HiddenRecord as a hidden class
// verification fails in the BSM to invoke equals(HiddenRecord, Object) method
record HiddenRecord(int i) { }
