#!/bin/bash
# scripts to update tree structure of the project and update git workspace
tree -I "out|target" > tree.txt
git add .
git commit # Edit the commit message in new opened Vim tab