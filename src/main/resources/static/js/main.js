function parseDateSent(currentDate) {
    const now = new Date();

    if(now > currentDate){
        if(now.getFullYear()>currentDate.getFullYear()){
            return now.getFullYear()-currentDate.getFullYear()+'y.'
        } else if(now.getMonth()>currentDate.getMonth()){
            return now.getMonth()-currentDate.getMonth()+'m.'
        } else if(now.getDate()>currentDate.getDate()){
            return now.getDate()-currentDate.getDate()+'d.'
        } else if(now.getHours()>currentDate.getHours()){
            return now.getHours()-currentDate.getHours()+'h.'
        } else if(now.getMinutes()>currentDate.getMinutes()){
            return now.getMinutes()-currentDate.getMinutes()+'m.'
        } else {
            return now.getSeconds()-currentDate.getSeconds()+'s.'
        }
    }
}

function parseDateToString(date) {
    return date.getDate()+'.'+date.getMonth()+'.'+date.getFullYear();
}

function getIncomingMessagesCount() {
    let res = 0;
    $.ajax({
        type : "GET",
        url : "/messages/get-incoming-messages-count",
        success : function(result) {
            if(result > 0)
                res = result;
        }
    });
    return res;
}