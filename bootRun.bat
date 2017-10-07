@echo off

set WEXPLORER_IMAGES_DIR=ignore/images
set WEXPLORER_MOVIES_DIR=ignore/movies
set WEXPLORER_LOG_LEVEL=DEBUG

call gradle bootRun
