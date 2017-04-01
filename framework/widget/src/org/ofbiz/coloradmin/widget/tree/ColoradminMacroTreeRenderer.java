package org.ofbiz.coloradmin.widget.tree;

import freemarker.template.TemplateException;
import org.ofbiz.widget.tree.MacroTreeRenderer;

import java.io.IOException;

/**
 * Created by Administrator on 2015/1/20.
 */
class ColoradminMacroTreeRenderer extends MacroTreeRenderer  {

    public ColoradminMacroTreeRenderer(String macroLibraryPath, Appendable writer) throws TemplateException, IOException {
        super(macroLibraryPath, writer);
    }
}
