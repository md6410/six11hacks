#!/usr/bin/env python
import sys
import ast

def parseProgramString(progStr, fileName):
    v = Visitor(fileName)
    tree = ast.parse(progStr, fileName, "exec")
    v.visit(tree)

class Visitor(ast.NodeVisitor):
    """
    Each node in the AST is passed to one of the Visitor's methods. By
    default, generic_visit is called, but if there is a corresponding
    visit_XYZ method for the node's type (XYZ in this example) that is
    called instead. Each of these visiting functions must call the
    superclass's generic_visit method if you want the node's children
    to be traversed.
    """
    def __init__(self, fileName):
        self.where = Where()
        self.where.file = fileName

    def generic_visit(self, node):
        ast.NodeVisitor.generic_visit(self, node)
    
    def visit_Name(self, node):
        print(str(self.where) + "\t" + node.id)

    def visit_Load(self, node):
        pass

    def visit_ClassDef(self, node):
        prev_val = self.where.klass
        self.where.klass = node.name
        ast.NodeVisitor.generic_visit(self, node)
        self.where.klass = prev_val

    def visit_FunctionDef(self, node):
        prev_val = self.where.method
        self.where.method = node.name
        ast.NodeVisitor.generic_visit(self, node)
        self.where.method = prev_val

class Where:
    def __init__(self):
        self.file = "?"
        self.klass = "?"
        self.method = "?"

    def __str__(self):
        return self.file + "\t" + self.klass + "\t" + self.method

    def report(self):
        return ""

if __name__ == "__main__":
    if (len(sys.argv) == 1): # perform analysis on this script itself
        fileObj = open(sys.argv[0], "r")
        fileContents = fileObj.read()
        parseProgramString(fileContents, sys.argv[0])
    else:
        for arg in sys.argv[1:]:
            fileObj = open(arg, "r")
            fileContents = fileObj.read()
            parseProgramString(fileContents, arg)
