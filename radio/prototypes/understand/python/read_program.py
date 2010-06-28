#!/usr/bin/env python
import sys
import ast

def explore(t, loc):
#    print("  Exploring node named " + str(t))
    if (isinstance(t, ast.Name)):
        print("Object: " + t.id + " in context: " + str(t.ctx._attributes))
    for field in ast.iter_fields(t):
        fieldName = field[0]
        fieldVal = field[1]
        try:
            values = iter(fieldVal)
            for val in values:
                if isinstance(val, ast.AST):
                    explore(val)
        except:
#            print(fieldName + " = " + str(fieldVal) + " (type: " + str(type(fieldVal)) + ")")
            pass

def parseProgramString(progStr):
    tree = ast.parse(progStr)
    explore(tree)

class Where:
    def __init__(self):
        self.file = "?"
        self.klass = "?"
        self.method = "?"

    def report(self):
        return ""

for arg in sys.argv:
    fileObj = open(arg, "r")
    fileContents = fileObj.read()
    parseProgramString(fileContents)

