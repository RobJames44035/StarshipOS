/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

// tidy: Warning: <a> cannot copy name attribute to id

/**
 * <a id="abc">valid</a>
 * <a id="abc123">valid</a>
 * <a id="a.1:2-3_4">valid</a>
 * <a id="foo()">valid</a>
 * <a id="foo() ">invalid</a>
 */
public class InvalidName { }
