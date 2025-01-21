/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.configuration;


/**
 * In which way we are going to keep class from being garbage collected
 *
 */
public enum KeepRefMode {
    STRONG_REFERENCE, SOFT_REFERENCE, STATIC_FIELD, STACK_LOCAL, THREAD_FIELD, THREAD_ITSELF, STATIC_FIELD_OF_ROOT_CLASS, JNI_GLOBAL_REF, JNI_LOCAL_REF

}
