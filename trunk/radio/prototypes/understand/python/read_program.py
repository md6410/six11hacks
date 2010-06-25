#!/usr/bin/env python
import sys
import ast

def explore(t):
    print("  Exploring node named " + str(t))
    for field in ast.iter_fields(t):
        fieldName = field[0]
        fieldVal = field[1]
        try:
            values = iter(fieldVal)
            for val in values:
                if isinstance(val, ast.AST):
                    explore(val)
                else:
                    print("Leaf: " + str(val))
        except:
            print(fieldName + " = " + str(fieldVal) + " (type: " + str(type(fieldVal)) + ")")

def parseProgramString(progStr):
    tree = ast.parse(progStr)
    explore(tree)

for arg in sys.argv:
    fileObj = open(arg, "r")
    fileContents = fileObj.read()
    parseProgramString(fileContents)

