function parseMessageDate(messageDate) {
    var monthNames = [
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ];
    return messageDate.getDate()+' '+monthNames[messageDate.getMonth()]+'. '+messageDate.getFullYear()+' '+messageDate.getHours()+':'+messageDate.getMinutes();
}