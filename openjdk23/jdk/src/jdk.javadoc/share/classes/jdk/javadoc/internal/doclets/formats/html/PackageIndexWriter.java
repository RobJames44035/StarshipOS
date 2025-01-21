/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html;

import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import javax.lang.model.element.PackageElement;

import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyles;
import jdk.javadoc.internal.doclets.toolkit.util.DocPaths;
import jdk.javadoc.internal.doclets.toolkit.util.Group;
import jdk.javadoc.internal.html.Content;
import jdk.javadoc.internal.html.ContentBuilder;
import jdk.javadoc.internal.html.Text;

/**
 * Generate the package index page "index.html".
 */
public class PackageIndexWriter extends AbstractOverviewIndexWriter {

    /**
     * The Set of Packages to be documented.
     */
    protected SortedSet<PackageElement> packages;

    /**
     * Construct the PackageIndexWriter. Also constructs the grouping
     * information as provided on the command line by "-group" option. Stores
     * the order of groups specified by the user.
     *
     * @param configuration the configuration for this doclet
     * @see Group
     */
    public PackageIndexWriter(HtmlConfiguration configuration) {
        super(configuration, DocPaths.INDEX);
        packages = configuration.packages;
    }

    @Override
    public String getDescription() {
        return "package index";
    }

    @Override
    public String getTitleKey() {
        return "doclet.Window_Overview_Summary";
    }

    /**
     * Adds the packages list to the documentation tree.
     *
     * @param target the content to which the packages list will be added
     */
    @Override
    protected void addIndex(Content target) {
        Map<String, SortedSet<PackageElement>> groupPackageMap
                = configuration.group.groupPackages(packages);

        if (!groupPackageMap.keySet().isEmpty()) {
            var table = new Table<PackageElement>(HtmlStyles.summaryTable)
                    .setHeader(getPackageTableHeader())
                    .setColumnStyles(HtmlStyles.colFirst, HtmlStyles.colLast)
                    .setId(HtmlIds.ALL_PACKAGES_TABLE)
                    .setDefaultTab(contents.getContent("doclet.All_Packages"));

            // add the tabs in command-line order
            for (String groupName : configuration.group.getGroupList()) {
                Set<PackageElement> groupPackages = groupPackageMap.get(groupName);
                if (groupPackages != null) {
                    table.addTab(Text.of(groupName), groupPackages::contains);
                }
            }

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
}
