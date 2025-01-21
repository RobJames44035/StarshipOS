/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html;

import javax.lang.model.element.PackageElement;

import jdk.javadoc.internal.doclets.formats.html.Navigation.PageMode;
import jdk.javadoc.internal.doclets.formats.html.markup.BodyContents;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyles;
import jdk.javadoc.internal.doclets.toolkit.util.DocFileIOException;
import jdk.javadoc.internal.doclets.toolkit.util.DocPaths;
import jdk.javadoc.internal.html.Content;
import jdk.javadoc.internal.html.ContentBuilder;
import jdk.javadoc.internal.html.HtmlTree;
import jdk.javadoc.internal.html.Text;

/**
 * Generate the file with list of all the packages in this run.
 */
public class AllPackagesIndexWriter extends HtmlDocletWriter {

    /**
     * Construct AllPackagesIndexWriter object.
     *
     * @param configuration The current configuration
     */
    public AllPackagesIndexWriter(HtmlConfiguration configuration) {
        super(configuration, DocPaths.ALLPACKAGES_INDEX);
    }

    /**
     * Print all the packages in the file.
     */
    @Override
    public void buildPage() throws DocFileIOException {
        String label = resources.getText("doclet.All_Packages");
        Content mainContent = new ContentBuilder();
        addPackages(mainContent);
        Content titleContent = contents.allPackagesLabel;
        var pHeading = HtmlTree.HEADING_TITLE(Headings.PAGE_TITLE_HEADING,
                HtmlStyles.title, titleContent);
        var headerDiv = HtmlTree.DIV(HtmlStyles.header, pHeading);
        HtmlTree body = getBody(getWindowTitle(label));
        body.add(new BodyContents()
                .setHeader(getHeader(PageMode.ALL_PACKAGES))
                .addMainContent(headerDiv)
                .addMainContent(mainContent)
                .setFooter(getFooter()));
        printHtmlDocument(null, "package index", body);
    }

    /**
     * Add all the packages to the content.
     *
     * @param target the content to which the links will be added
     */
    protected void addPackages(Content target) {
        var table = new Table<PackageElement>(HtmlStyles.summaryTable)
                .setCaption(Text.of(contents.packageSummaryLabel.toString()))
                .setHeader(new TableHeader(contents.packageLabel, contents.descriptionLabel))
                .setColumnStyles(HtmlStyles.colFirst, HtmlStyles.colLast);
        for (PackageElement pkg : configuration.packages) {
            if (!(options.noDeprecated() && utils.isDeprecated(pkg))) {
                Content packageLinkContent = getPackageLink(pkg, getLocalizedPackageName(pkg));
                Content summaryContent = new ContentBuilder();
                addSummaryComment(pkg, summaryContent);
                table.addRow(pkg, packageLinkContent, summaryContent);
            }
        }
        target.add(table);
    }
}
