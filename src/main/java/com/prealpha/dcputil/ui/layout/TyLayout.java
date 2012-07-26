package com.prealpha.dcputil.ui.layout;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Ty
 * Date: 7/26/12
 * Time: 7:57 AM
 */
public class TyLayout implements LayoutManager2 {
    public static abstract class Resize {
        public abstract Rectangle resize(Component parent);
    }

    private Map<Component,Resize> componentMap = new HashMap<Component, Resize>();
    private final int padding;

    public TyLayout(){
        padding = 0;
    }

    public TyLayout(int padding){
        this.padding = padding;
    }

    @Override
    public void addLayoutComponent(String dimensionals, Component comp) {
        throw new IllegalArgumentException("Fuck you, don't use this one");
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints){
        if(!(constraints instanceof Resize)){
            throw new IllegalArgumentException("Constraints must be a type of Resize");
        }
        Resize resize = (Resize) constraints;

        componentMap.put(comp,resize);
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        componentMap.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return parent.getPreferredSize();
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return parent.getMinimumSize();
    }

    @Override
    public void layoutContainer(Container parent) {
        for(Component component: this.componentMap.keySet()){
            Resize r = this.componentMap.get(component);
            Rectangle rect = r.resize(parent);
            if(this.padding!=0){
                rect = new Rectangle(rect.x+padding,rect.y+padding,rect.width-padding*2,rect.height-padding*2);
            }
            component.setBounds(rect);
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return target.getMaximumSize();
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    @Override
    public void invalidateLayout(Container target) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
