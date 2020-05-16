package org.dbflute.erflute.editor.persistent.xml.writer;

import org.dbflute.erflute.core.util.Format;
import org.dbflute.erflute.core.util.NameValue;
import org.dbflute.erflute.editor.model.dbexport.ddl.DDLTarget;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.DiagramWalker;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.category.Category;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.model_properties.ModelProperties;
import org.dbflute.erflute.editor.model.diagram_contents.element.node.table.properties.TableProperties;
import org.dbflute.erflute.editor.model.settings.CategorySettings;
import org.dbflute.erflute.editor.model.settings.DBSettings;
import org.dbflute.erflute.editor.model.settings.DiagramSettings;
import org.dbflute.erflute.editor.model.settings.Environment;
import org.dbflute.erflute.editor.model.settings.EnvironmentSettings;
import org.dbflute.erflute.editor.model.settings.ExportSettings;
import org.dbflute.erflute.editor.model.settings.PageSettings;
import org.dbflute.erflute.editor.model.settings.design.ConstraintSettings;
import org.dbflute.erflute.editor.model.settings.design.DesignSettings;
import org.dbflute.erflute.editor.persistent.xml.PersistentXml;
import org.dbflute.erflute.editor.persistent.xml.PersistentXml.PersistentContext;

/**
 * @author modified by jflute (originated in ermaster)
 */
public class WrittenSettingsBuilder {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final PersistentXml persistentXml;
    protected final WrittenAssistLogic assistLogic;
    protected final WrittenDiagramWalkerBuilder nodeElementBuilder;
    protected final WrittenTablePropertiesBuilder tablePropertiesBuilder;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public WrittenSettingsBuilder(PersistentXml persistentXml, WrittenAssistLogic assistLogic,
            WrittenDiagramWalkerBuilder nodeElementBuilder, WrittenTablePropertiesBuilder tablePropertiesBuilder) {
        this.persistentXml = persistentXml;
        this.assistLogic = assistLogic;
        this.nodeElementBuilder = nodeElementBuilder;
        this.tablePropertiesBuilder = tablePropertiesBuilder;
    }

    // ===================================================================================
    //                                                                          DB Setting
    //                                                                          ==========
    public String buildDBSetting(DBSettings dbSetting) {
        final StringBuilder xml = new StringBuilder();
        xml.append("<dbsystem>").append(escape(dbSetting.getDbsystem())).append("</dbsystem>\n");
        xml.append("<server>").append(escape(dbSetting.getServer())).append("</server>\n");
        xml.append("<port>").append(dbSetting.getPort()).append("</port>\n");
        xml.append("<database>").append(escape(dbSetting.getDatabase())).append("</database>\n");
        xml.append("<user>").append(escape(dbSetting.getUser())).append("</user>\n");
        xml.append("<password>").append(escape(dbSetting.getPassword())).append("</password>\n");
        xml.append("<use_default_driver>").append(dbSetting.isUseDefaultDriver()).append("</use_default_driver>\n");
        xml.append("<url>").append(escape(dbSetting.getUrl())).append("</url>\n");
        xml.append("<driver_class_name>").append(escape(dbSetting.getDriverClassName())).append("</driver_class_name>\n");
        return xml.toString();
    }

    // ===================================================================================
    //                                                                       Page Settings
    //                                                                       =============
    public String buildPageSettings(PageSettings settings) {
        final StringBuilder xml = new StringBuilder();
        xml.append("<direction_horizontal>").append(settings.isDirectionHorizontal()).append("</direction_horizontal>\n");
        xml.append("<scale>").append(settings.getScale()).append("</scale>\n");
        xml.append("<paper_size>").append(escape(settings.getPaperSize())).append("</paper_size>\n");
        xml.append("<top_margin>").append(settings.getTopMargin()).append("</top_margin>\n");
        xml.append("<left_margin>").append(settings.getLeftMargin()).append("</left_margin>\n");
        xml.append("<bottom_margin>").append(settings.getBottomMargin()).append("</bottom_margin>\n");
        xml.append("<right_margin>").append(settings.getRightMargin()).append("</right_margin>\n");
        return xml.toString();
    }

    // ===================================================================================
    //                                                                    Diagram Settings
    //                                                                    ================
    public String buildDiagramSettings(DiagramSettings settings, PersistentContext context) {
        final StringBuilder xml = new StringBuilder();
        xml.append("<diagram_settings>\n");
        xml.append("\t<database>").append(escape(settings.getDatabase())).append("</database>\n");
        xml.append("\t<capital>").append(settings.isCapital()).append("</capital>\n");
        xml.append("\t<table_style>").append(escape(settings.getTableStyle())).append("</table_style>\n");
        xml.append("\t<notation>").append(escape(settings.getNotation())).append("</notation>\n");
        xml.append("\t<notation_level>").append(settings.getNotationLevel()).append("</notation_level>\n");
        xml.append("\t<notation_expand_group>").append(settings.isNotationExpandGroup()).append("</notation_expand_group>\n");
        xml.append("\t<view_mode>").append(settings.getViewMode()).append("</view_mode>\n");
        xml.append("\t<outline_view_mode>").append(settings.getOutlineViewMode()).append("</outline_view_mode>\n");
        xml.append("\t<view_order_by>").append(settings.getViewOrderBy()).append("</view_order_by>\n");
        xml.append("\t<auto_ime_change>").append(settings.isAutoImeChange()).append("</auto_ime_change>\n");
        xml.append("\t<validate_physical_name>").append(settings.isValidatePhysicalName()).append("</validate_physical_name>\n");
        xml.append("\t<use_bezier_curve>").append(settings.isUseBezierCurve()).append("</use_bezier_curve>\n");
        xml.append("\t<suspend_validator>").append(settings.isSuspendValidator()).append("</suspend_validator>\n");
        xml.append("\t<titleFontEm>").append(settings.getTitleFontEm().toString()).append("</titleFontEm>\n");
        xml.append("\t<masterDataBasePath>").append(settings.getMasterDataBasePath().toString()).append("</masterDataBasePath>\n");
        xml.append("\t<use_view_object>").append(settings.isUseViewObject()).append("</use_view_object>\n");
        xml.append(tab(buildExportSetting(settings.getExportSettings(), context)));
        xml.append(tab(buildCategorySettings(settings.getCategorySetting(), context)));
        xml.append(tab(buildModelProperties(settings.getModelProperties(), context)));
        xml.append(tab(buildTableProperties((TableProperties) settings.getTableViewProperties(), context))); // #thnking why downcast
        xml.append(tab(buildEnvironmentSetting(settings.getEnvironmentSettings(), context)));
        xml.append(tab(buildDesignSetting(settings.getDesignSettings(), context)));
        xml.append("</diagram_settings>\n");
        return xml.toString();
    }

    // ===================================================================================
    //                                                                     Export Settings
    //                                                                     ===============
    private String buildExportSetting(ExportSettings settings, PersistentContext context) {
        final StringBuilder xml = new StringBuilder();
        xml.append("<export_settings>\n");
        xml.append("\t<category_name_to_export>").append(escape(settings.getCategoryNameToExport())).append("</category_name_to_export>\n");
        xml.append("\t<ddl_output>").append(escape(settings.getDdlOutput())).append("</ddl_output>\n");
        xml.append("\t<excel_output>").append(escape(settings.getExcelOutput())).append("</excel_output>\n");
        xml.append("\t<excel_template>").append(escape(settings.getExcelTemplate())).append("</excel_template>\n");
        xml.append("\t<image_output>").append(escape(settings.getImageOutput())).append("</image_output>\n");
        xml.append("\t<put_diagram_on_excel>").append(settings.isPutERDiagramOnExcel()).append("</put_diagram_on_excel>\n");
        xml.append("\t<use_logical_name_as_sheet>").append(settings.isUseLogicalNameAsSheet()).append("</use_logical_name_as_sheet>\n");
        xml.append("\t<open_after_saved>").append(settings.isOpenAfterSaved()).append("</open_after_saved>\n");
        final DDLTarget ddlTarget = settings.getDdlTarget();
        xml.append("\t<create_comment>").append(ddlTarget.createComment).append("</create_comment>\n");
        xml.append("\t<create_foreignKey>").append(ddlTarget.createForeignKey).append("</create_foreignKey>\n");
        xml.append("\t<create_index>").append(ddlTarget.createIndex).append("</create_index>\n");
        xml.append("\t<create_sequence>").append(ddlTarget.createSequence).append("</create_sequence>\n");
        xml.append("\t<create_table>").append(ddlTarget.createTable).append("</create_table>\n");
        xml.append("\t<create_tablespace>").append(ddlTarget.createTablespace).append("</create_tablespace>\n");
        xml.append("\t<create_trigger>").append(ddlTarget.createTrigger).append("</create_trigger>\n");
        xml.append("\t<create_view>").append(ddlTarget.createView).append("</create_view>\n");
        xml.append("\t<drop_index>").append(ddlTarget.dropIndex).append("</drop_index>\n");
        xml.append("\t<drop_sequence>").append(ddlTarget.dropSequence).append("</drop_sequence>\n");
        xml.append("\t<drop_table>").append(ddlTarget.dropTable).append("</drop_table>\n");
        xml.append("\t<drop_tablespace>").append(ddlTarget.dropTablespace).append("</drop_tablespace>\n");
        xml.append("\t<drop_trigger>").append(ddlTarget.dropTrigger).append("</drop_trigger>\n");
        xml.append("\t<drop_view>").append(ddlTarget.dropView).append("</drop_view>\n");
        xml.append("\t<inline_column_comment>").append(ddlTarget.inlineColumnComment).append("</inline_column_comment>\n");
        xml.append("\t<inline_table_comment>").append(ddlTarget.inlineTableComment).append("</inline_table_comment>\n");
        xml.append("\t<comment_value_description>").append(ddlTarget.commentValueDescription).append("</comment_value_description>\n");
        xml.append("\t<comment_value_logical_name>").append(ddlTarget.commentValueLogicalName).append("</comment_value_logical_name>\n");
        xml.append("\t<comment_value_logical_name_description>")
                .append(ddlTarget.commentValueLogicalNameDescription)
                .append("</comment_value_logical_name_description>\n");
        xml.append("\t<comment_replace_line_feed>").append(ddlTarget.commentReplaceLineFeed).append("</comment_replace_line_feed>\n");
        xml.append("\t<comment_replace_string>")
                .append(Format.null2blank(ddlTarget.commentReplaceString))
                .append("</comment_replace_string>\n");
        xml.append("</export_settings>\n");
        return xml.toString();
    }

    // ===================================================================================
    //                                                                   Category Settings
    //                                                                   =================
    private String buildCategorySettings(CategorySettings settings, PersistentContext context) {
        final StringBuilder xml = new StringBuilder();
        xml.append("<category_settings>\n");
        xml.append("\t<free_layout>").append(settings.isFreeLayout()).append("</free_layout>\n");
        xml.append("\t<show_referred_tables>").append(settings.isShowReferredTables()).append("</show_referred_tables>\n");
        xml.append("\t<categories>\n");
        for (final Category category : settings.getAllCategories()) {
            xml.append(tab(tab(doBuildCategory(category, settings.isSelected(category), context))));
        }
        xml.append("\t</categories>\n");
        xml.append("</category_settings>\n");
        return xml.toString();
    }

    private String doBuildCategory(Category category, boolean isSelected, PersistentContext context) {
        final StringBuilder xml = new StringBuilder();
        xml.append("<category>\n");
        xml.append(tab(nodeElementBuilder.buildWalker(category, context)));
        xml.append("\t<name>").append(escape(category.getName())).append("</name>\n");
        xml.append("\t<selected>").append(isSelected).append("</selected>\n");
        for (final DiagramWalker walker : category.getContents()) {
            xml.append("\t<category_contents>").append(context.walkerMap.get(walker)).append("</category_contents>\n");
        }
        xml.append("</category>\n");
        return xml.toString();
    }

    // ===================================================================================
    //                                                                    Model Properties
    //                                                                    ================
    private String buildModelProperties(ModelProperties modelProperties, PersistentContext context) {
        final StringBuilder xml = new StringBuilder();
        xml.append("<model_properties>\n");
        xml.append(tab(nodeElementBuilder.buildWalker(modelProperties, context)));
        xml.append("\t<display>").append(modelProperties.isDisplay()).append("</display>\n");
        for (final NameValue property : modelProperties.getProperties()) {
            xml.append(tab(doBuildModelProperty(property, context)));
        }
        xml.append("</model_properties>\n");
        return xml.toString();
    }

    private String doBuildModelProperty(NameValue property, PersistentContext context) {
        final StringBuilder xml = new StringBuilder();
        xml.append("<model_property>\n");
        xml.append("\t<name>").append(escape(property.getName())).append("</name>\n");
        xml.append("\t<value>").append(escape(property.getValue())).append("</value>\n");
        xml.append("</model_property>\n");
        return xml.toString();
    }

    // ===================================================================================
    //                                                                    Table Properties
    //                                                                    ================
    private String buildTableProperties(TableProperties properties, PersistentContext context) {
        return tablePropertiesBuilder.buildTableProperties(properties, context);
    }

    // ===================================================================================
    //                                                                Environment Settings
    //                                                                ====================
    private String buildEnvironmentSetting(EnvironmentSettings environmentSetting, PersistentContext context) {
        final StringBuilder xml = new StringBuilder();
        xml.append("<environment_settings>\n");
        for (final Environment environment : environmentSetting.getEnvironments()) {
            xml.append("\t<environment>\n");
            final Integer environmentId = context.environmentMap.get(environment);
            xml.append("\t\t<id>").append(environmentId).append("</id>\n");
            xml.append("\t\t<name>").append(environment.getName()).append("</name>\n");
            xml.append("\t</environment>\n");
        }
        xml.append("</environment_settings>\n");
        return xml.toString();
    }

    // ===================================================================================
    //                                                                     Design Settings
    //                                                                     ===============
    private String buildDesignSetting(DesignSettings designSettings, PersistentContext context) {
        if (designSettings.isEmpty()) {
            return "";
        }
        final StringBuilder xml = new StringBuilder();
        xml.append("<design_settings>\n");
        final ConstraintSettings constraintSettings = designSettings.getConstraintSettings();
        if (!constraintSettings.isEmpty()) {
            {
                xml.append("\t<foreign_key>\n");
                final String prefix = constraintSettings.getDefaultPrefixOfForeignKey();
                if (prefix != null) {
                    xml.append("\t\t<default_prefix>").append(prefix).append("</default_prefix>\n");
                }
                xml.append("\t</foreign_key>\n");
            }
            {
                xml.append("\t<unique>\n");
                final String prefix = constraintSettings.getDefaultPrefixOfUnique();
                if (prefix != null) {
                    xml.append("\t\t<default_prefix>").append(prefix).append("</default_prefix>\n");
                }
                xml.append("\t</unique>\n");
            }
            {
                xml.append("\t<index>\n");
                final String prefix = constraintSettings.getDefaultPrefixOfIndex();
                if (prefix != null) {
                    xml.append("\t\t<default_prefix>").append(prefix).append("</default_prefix>\n");
                }
                xml.append("\t</index>\n");
            }
        }
        xml.append("</design_settings>\n");
        return xml.toString();
    }

    // ===================================================================================
    //                                                                        Assist Logic
    //                                                                        ============
    private String tab(String str) {
        return assistLogic.tab(str);
    }

    private String escape(String s) {
        return assistLogic.escape(s);
    }
}
