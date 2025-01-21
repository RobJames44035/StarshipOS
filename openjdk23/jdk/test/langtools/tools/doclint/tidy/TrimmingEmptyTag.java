/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

// tidy: Warning: trimming empty <.*>

/**
 * <b></b>
 * <table><caption></caption></table>
 * <code></code>
 * <dl></dl>
 * <dl><dt></dt><dd></dd></dl>
 * <i></i>
 * <ol></ol>
 * <p></p>
 * <pre></pre>
 * <span></span>
 * <ul></ul>
 * <ul><li></li></ul>
 */
public class TrimmingEmptyTag {
    /** <p> */
    public void implicitParaEnd_endOfComment() { }
    /** <p> <ul><li>text</ul> */
    public void implicitParaEnd_nextBlockTag() { }
}
