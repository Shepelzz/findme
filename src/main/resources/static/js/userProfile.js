var loader = $('#loaderGif');



function acceptFriendRequest(id) {
    debugger;
    loader.show();
    $.ajax({
        type: "POST",
        url: "/friend-request-accept/" + id,
        success: function success() {
            loader.hide();
            $('#incomingReq-' + id).hide();
        },
        error: function(xhr) {
            loader.hide();
            // alert(xhr.responseText);
        }
    });
}

function rejectFriendRequest(id) {
    loader.show();
    $.ajax({
        type: "POST",
        url: "/friend-request-reject/" + id,
        success: function success() {
            loader.hide();
            $('#incomingReq-' + id).hide();
        },
        error: function(xhr) {
            loader.hide();
            // alert(xhr.responseText);
        }
    });
}

function cancelRequestToFriend(id) {
    loader.show();
    $.ajax({
        type: "POST",
        url: "/request-cancel/" + id,
        success: function success() {
            loader.hide();
            $('#outgoingReq-' + id).hide();
            $('#reqSent').hide();
        },
        error: function(xhr) {
            loader.hide();
            alert(xhr.responseText);
        }
    });
}

function addFriend(id) {
    loader.show();
    debugger;
    $.ajax({
        type: "POST",
        url: "/friend-add/" + id,
        success: function success() {
            loader.hide();
        },
        error: function(xhr) {
            loader.hide();
            alert(xhr.responseText);
        }
    });
}

function deleteFriend(id) {
    loader.show();
    debugger;
    $.ajax({
        type: "POST",
        url: "/friend-remove/" + id,
        success: function success() {
            loader.hide();
        },
        error: function(xhr) {
            loader.hide();
            alert(xhr.responseText);
        }
    });
}
