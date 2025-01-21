/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html;

import java.util.List;
import java.util.Objects;

import jdk.javadoc.internal.doclets.formats.html.markup.Head;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlDocument;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyles;
import jdk.javadoc.internal.doclets.toolkit.util.DocFile;
import jdk.javadoc.internal.doclets.toolkit.util.DocFileIOException;
import jdk.javadoc.internal.doclets.toolkit.util.DocPath;
import jdk.javadoc.internal.doclets.toolkit.util.DocPaths;
import jdk.javadoc.internal.html.ContentBuilder;
import jdk.javadoc.internal.html.HtmlAttr;
import jdk.javadoc.internal.html.HtmlTag;
import jdk.javadoc.internal.html.HtmlTree;
import jdk.javadoc.internal.html.Script;
import jdk.javadoc.internal.html.Text;

/**
 * Writes a file that tries to redirect to an alternate page.
 * The redirect uses JavaScript, if enabled, falling back on
 * {@code <meta http-equiv=refresh content="0,<uri>">}.
 * If neither are supported/enabled in a browser, the page displays the
 * standard "JavaScript not enabled" message, and a link to the alternate page.
 */
public class IndexRedirectWriter extends HtmlDocletWriter {

    public static void generate(HtmlConfiguration configuration)
            throws DocFileIOException {
        generate(configuration, DocPaths.INDEX, configuration.topFile);
    }

    public static void generate(HtmlConfiguration configuration, DocPath fileName, DocPath target)
            throws DocFileIOException {
        var indexRedirect = new IndexRedirectWriter(configuration, fileName, target);
        indexRedirect.buildPage();
    }

    private final DocPath target;

    private IndexRedirectWriter(HtmlConfiguration configuration, DocPath filename, DocPath target) {
        super(configuration, filename);
        assert target != null && !target.isEmpty() && !Objects.equals(target, filename)
                : "target: '" + target.getPath() + "'";
        this.target = target;
    }

    @Override
    public void buildPage() throws DocFileIOException {
        Head head = new Head(path, configuration.getDocletVersion(), configuration.getBuildDate())
                .setTimestamp(!options.noTimestamp())
                .setDescription("index redirect")
                .setGenerator(getGenerator(getClass()))
                .setStylesheets(configuration.getMainStylesheet(), List.of(), List.of())
                .addDefaultScript(false);

        String title = (options.windowTitle().length() > 0)
                ? options.windowTitle()
                : resources.getText("doclet.Generated_Docs_Untitled");

        head.setTitle(title)
                .setCharset(options.charset())
                .setCanonicalLink(target);

        String targetPath = target.getPath();
        Script script = new Script("window.location.replace(")
                .appendStringLiteral(targetPath, '\'')
                .append(")");
        var metaRefresh = HtmlTree.of(HtmlTag.META)
                .put(HtmlAttr.HTTP_EQUIV, "Refresh")
                .put(HtmlAttr.CONTENT, "0;" + targetPath);
        head.addContent(script.asContent(), HtmlTree.NOSCRIPT(metaRefresh));

        ContentBuilder bodyContent = new ContentBuilder();
        bodyContent.add(HtmlTree.NOSCRIPT(
                HtmlTree.P(contents.getContent("doclet.No_Script_Message"))));

        bodyContent.add(HtmlTree.P(HtmlTree.A(targetPath, Text.of(targetPath))));

        var body = HtmlTree.BODY(HtmlStyles.indexRedirectPage)
                .add(HtmlTree.MAIN(bodyContent));

        HtmlDocument htmlDocument = new HtmlDocument(
                HtmlTree.HTML(configuration.getLocale().getLanguage(), head, body));
        htmlDocument.write(DocFile.createFileForOutput(configuration, path));
    }
}
