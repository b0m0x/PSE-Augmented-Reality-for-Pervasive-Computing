#!/bin/bash
javadoc -sourcepath Vision/src -subpackages -package vision -package vision.model -subpackages vision.controller vision.view -doclet com.aurigalogic.doclet.core.Doclet -docletpath aurigadoclet/bin/AurigaDoclet.jar -format pdf -out doc.pdf

