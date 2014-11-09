package com.karateca.protractor;

import com.intellij.codeInsight.hint.HintManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiFile;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestLocatorAction extends AnAction {
  @Override
  public void update(AnActionEvent e) {
    e.getPresentation().setEnabled(canEnableAction(e));
  }

  boolean canEnableAction(AnActionEvent e) {
    Editor editor = e.getData(PlatformDataKeys.EDITOR);
    PsiFile file = e.getData(LangDataKeys.PSI_FILE);

    // Need the following objects.
    return editor != null &&
        file != null
        && e.getProject() != null &&
        editor.getSelectionModel().hasSelection();
  }

  public void actionPerformed(AnActionEvent actionEvent) {
    // No selection? Bail out.
    final Editor editor = actionEvent.getData(PlatformDataKeys.EDITOR);
    if (editor == null || !editor.getSelectionModel().hasSelection()) {
      return;
    }

    final AsyncLocatorTester tester = new AsyncLocatorTester(
        new ElementExplorerReader()
    );

    tester.addResultsReadyListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent changeEvent) {
        String hint = "";
        if (changeEvent.getSource() instanceof Pair) {
          Pair pair = (Pair) changeEvent.getSource();
          hint = String.format("%s", pair.second);
        }

        HintManager.getInstance().showInformationHint(editor, hint);
      }
    });

    String selectedText = editor.getSelectionModel().getSelectedText();
    if (selectedText == null) {
      HintManager
          .getInstance()
          .showErrorHint(editor, "Selection is empty");
      return;
    }

    tester.testLocator(selectedText);
  }
}
