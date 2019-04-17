function parsePostTaggedUsersAndLocation(usersList, location) {
    let res = '';
    if(usersList!=null && usersList.length > 0){
        res = res.concat('with ');
        $.each(usersList, function(i, user){
            res = res.concat(user.firstName, ' ', user.lastName.substring(0, 1), '.');
            if(i !== usersList.length-1)
                res = res.concat(usersList.length === 2 ? ' and ' : ', ');
        });
    }
    if(location != null && location !== '') {
        res = res.concat(' in ',location);
    }
    return res;
}

function parsePostDate(postDate) {
    var monthNames = [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ];
    return postDate.getDate()+' '+monthNames[postDate.getMonth()]+'. '+postDate.getFullYear()+' '+postDate.getHours()+':'+postDate.getMinutes();
}