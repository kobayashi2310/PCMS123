// 利用可能日程チェック
const availableRanges = [
    {start: '2025-05-15', end: '2025-05-20'},
    {start: '2025-05-22', end: '2025-05-25'}
];
function isInAvailableRange(dateStr) {
    const d = new Date(dateStr);
    return availableRanges.some(r => {
        const start = new Date(r.start);
        const end = new Date(r.end + 'T23:59:59');
        return d >= start && d <= end;
    });
}
document.getElementById('reservationForm').addEventListener('input', function() {
    const start = document.getElementById('startDateTime').value;
    const end = document.getElementById('endDateTime').value;
    const warning = document.getElementById('dateWarning');
    if (start && !isInAvailableRange(start.substring(0,10)) || end && !isInAvailableRange(end.substring(0,10))) {
        warning.classList.remove('d-none');
    } else {
        warning.classList.add('d-none');
    }
});
// 利用目的「その他」選択時のテキストエリア表示
const purposeSelect = document.getElementById('purpose');
const otherBox = document.getElementById('otherPurposeBox');
purposeSelect.addEventListener('change', function() {
    if (this.value === 'その他') {
        otherBox.classList.remove('d-none');
    } else {
        otherBox.classList.add('d-none');
    }
});