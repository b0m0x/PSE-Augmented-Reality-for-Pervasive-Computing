#!/bin/bash
javadoc -sourcepath src -subpackages -package vision -package vision.model -subpackages vision.controller vision.view -doclet com.aurigalogic.doclet.core.Doclet -docletpath ~/Downloads/aurigadoclet/bin/AurigaDoclet.jar -format pdf -out doc.pdf

