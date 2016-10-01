package org.insightech.er.editor.view.action.option.notation.design;

import org.insightech.er.editor.MainModelEditor;

public class ChangeDesignToFrameAction extends AbstractChangeDesignAction {

    public static final String ID = ChangeDesignToFrameAction.class.getName();

    public static final String TYPE = "frame";

    public ChangeDesignToFrameAction(MainModelEditor editor) {
        super(ID, TYPE, editor);
    }

}
