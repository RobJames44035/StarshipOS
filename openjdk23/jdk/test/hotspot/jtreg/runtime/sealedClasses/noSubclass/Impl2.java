/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package noSubclass;

final class ImplC {}
non-sealed class ImplCIntermediate extends BaseC {}
final class ImplCIndirect extends ImplCIntermediate {}

interface ImplII {}
non-sealed interface ImplIIntermediateI extends BaseI {}
interface ImplIIndirectI extends ImplIIntermediateI {}

final class ImplIC {}
non-sealed class ImplIIntermediateC implements BaseI {}
final class ImplIIndirectC extends ImplIIntermediateC {}
