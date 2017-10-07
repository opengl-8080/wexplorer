$(function() {
    var $viewerControl = $('#viewerControl');
    var $viewer = $('#viewer');
    var $image = $('#image');

    var config = JSON.parse(localStorage.getItem('config')) || {
        showViewerControl: false
    };
    
    if (config.showViewerControl) {
        $viewerControl.removeClass('viewer-control--invisible');
    }
    
    $viewer.on('click', function() {
        config.showViewerControl = $viewerControl.toggleClass('viewer-control--invisible').is(':visible');
        localStorage.setItem('config', JSON.stringify(config));
    });
    
    var windowWidth = $(window).width();
    var windowHeight = $(window).height();
    
    $image.height(windowHeight);
    
    if (windowWidth < $image.width()) {
        var imageWidth = $image.width();
        var imageHeight = $image.height();
        var aspectRate = windowWidth / imageWidth;
        
        alert(imageWidth + ':' + imageHeight)
        $image.width(windowWidth).height(imageHeight * aspectRate);
    }
});
