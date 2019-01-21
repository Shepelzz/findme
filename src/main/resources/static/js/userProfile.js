
function acceptFriendRequest(id) {
    $.ajax({
        type: "POST",
        url: "/friend-request-accept/" + id,
        // dataType: "text",
        // data: {"status": "suqya"},
        success: function success() {
            $('#incomingReq-' + id).hide();
        },
        error: function(xhr) {
            // alert(xhr.responseText);
        }
    });
}

function rejectFriendRequest(id) {
    $.ajax({
        type: "POST",
        url: "/friend-request-reject/" + id,
        success: function success() {
            $('#incomingReq-' + id).hide();
        },
        error: function(xhr) {
            // alert(xhr.responseText);
        }
    });
}

function cancelRequestToFriend(id) {
    $.ajax({
        type: "POST",
        url: "/request-cancel/" + id,
        success: function success() {
            $('#outgoingReq-' + id).hide();
            $('#reqSent').hide();
        },
        error: function(xhr) {
            alert(xhr.responseText);
        }
    });
}

function addFriend(id) {
    $.ajax({
        type: "POST",
        url: "/friend-add/" + id,
        success: function success() {
        },
        error: function(xhr) {
            alert(xhr.responseText);
        }
    });
}

function deleteFriend(id) {
    $.ajax({
        type: "POST",
        url: "/friend-remove/" + id,
        success: function success() {
        },
        error: function(xhr) {
            alert(xhr.responseText);
        }
    });
}
