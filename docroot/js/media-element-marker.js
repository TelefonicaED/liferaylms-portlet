(function ($) {
    // markers

    $.extend(mejs.MepDefaults, {
        markerColor: '#E9BC3D', //default marker color
        markers: [],
        markerCallback: function () {

        }
    });

    $.extend(MediaElementPlayer.prototype, {
        buildmarkers: function (player, controls, layers, media) {
            var t = this,
                i = 0,
                currentPos = -1,
                currentMarker = -1,
                lastPlayPos = -1, //Track backward seek
                lastMarkerCallBack = -1; //Prevents successive firing of callbacks
           
            for (i = 0; i < player.options.markers.length; ++i) {
                $('.mejs__time-total').append('<span class="mejs-time-marker" id="marker-'+player.options.markers[i][0]+'"></span>');
            }
            

            media.addEventListener('play', function (e) {
            	console.log("PLAYYY!");
                player.setmarkers();
            }, false);
            
     
            
            media.addEventListener('timeupdate', function (e) {
                currentPos = Math.floor(media.currentTime);
                if (lastPlayPos > currentPos) {
                    if (lastMarkerCallBack > currentPos) {
                        lastMarkerCallBack = -1;
                    }
                } else {
                    lastPlayPos = currentPos;
                }

                for (i = 0; i < player.options.markers.length; ++i) {
                    currentMarker = Math.floor(player.options.markers[i][1]); 
                    if (currentPos === currentMarker && currentMarker !== lastMarkerCallBack) {
                        player.options.markerCallback(media, media.currentTime, player.options.markers[i][0]); //Fires the callback function
                        lastMarkerCallBack = currentMarker;
                    }
                }

            }, false);

        },
        setmarkers: function () {
            var t = this,
                i = 0,
                left;
            console.log("SETTINGss MARKER "+t.media.getDuration());
            for (i = 0; i < t.options.markers.length; ++i) {
                console.log("MARKERS "+ i);
            	if (Math.floor(t.options.markers[i][1]) <= t.media.getDuration() && Math.floor(t.options.markers[i][1]) >= 0) {
                    left = 100 * Math.floor(t.options.markers[i][1]) / t.media.getDuration();
                    console.log("SET CSS "+ i);
                    $($('.mejs-time-marker')[i]).css({
                        "width": "2px",
                        "height": "10px",
                        "left": left+"%",
                        "position": "absolute",
                        "background": t.options.markerColor
                    });
                }
            }
            $(".vp-controls-wrapper").innerHTML="";
        }
    });
})(mejs.$);