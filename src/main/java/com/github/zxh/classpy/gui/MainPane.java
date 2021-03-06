package com.github.zxh.classpy.gui;

import com.github.zxh.classpy.classfile.MethodInfo;
import com.github.zxh.classpy.classfile.ClassComponent;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;

/**
 * Container of TreeView, HexPane and StatusBar.
 * 
 * |------------------------------|
 * | TreeView      |      HexPane |
 * |               |              |
 * |------------------------------|
 * | StatusLabel          BytesBar|
 * |------------------------------|
 */
public class MainPane extends BorderPane {
    
    private final TreeView<ClassComponent> tree;
    private final HexPane hexPane;
    private final Label statusLabel;
    private final BytesBar bytesBar;
    
    public MainPane(ClassComponent file, FileHex hex) {
        tree = buildClassTree(file);
        hexPane = new HexPane(hex);
        statusLabel = new Label(" ");
        bytesBar = new BytesBar(file.getLength());
        bytesBar.setMaxHeight(statusLabel.getPrefHeight());
        bytesBar.setPrefWidth(100);
        
        super.setCenter(buildSplitPane());
        super.setBottom(buildStatusBar());
        listenTreeItemSelection();
    }
    
    public MethodInfo getSelectedMethodInfo() {
        TreeItem<ClassComponent> fcItem = tree.getSelectionModel().getSelectedItem();
        if (fcItem != null) {
            ClassComponent fc = fcItem.getValue();
            if (fc instanceof MethodInfo) {
                return (MethodInfo) fc;
            }
        }
        
        return null;
    }
    
    private static TreeView<ClassComponent> buildClassTree(ClassComponent file) {
        ClassComponentTreeItem root = new ClassComponentTreeItem(file);
        root.setExpanded(true);
        
        TreeView<ClassComponent> tree = new TreeView<>(root);
        tree.setMinWidth(200);
        
        return tree;
    }
    
    private SplitPane buildSplitPane() {
        SplitPane sp = new SplitPane();
        sp.getItems().add(tree);
        sp.getItems().add(hexPane);
        sp.setDividerPositions(0.3, 0.7);
        return sp;
    }
    
    private BorderPane buildStatusBar() {
        BorderPane statusBar = new BorderPane();
        statusBar.setLeft(statusLabel);
        statusBar.setRight(bytesBar);
        return statusBar;
    }
    
    private void listenTreeItemSelection() {
        tree.getSelectionModel().getSelectedItems().addListener(
            (ListChangeListener.Change<? extends TreeItem<ClassComponent>> c) -> {
                if (c.next()) {
                    if (c.wasAdded()) {
                        TreeItem<ClassComponent> node = c.getList().get(c.getFrom());
                        if (node != null && node.getParent() != null) {
                            ClassComponent fc = node.getValue();
                            //System.out.println("select " + cc);
                            statusLabel.setText(" " + fc.getClass().getSimpleName());
                            if (fc.getLength() > 0) {
                                hexPane.select(fc);
                                bytesBar.select(fc);
                            }
                        }
                    }
                }
            }
        );
    }
    
}
