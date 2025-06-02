document.addEventListener('DOMContentLoaded', function() {
    const dateInput = document.getElementById('date');
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const day = String(today.getDate()).padStart(2, '0');
    const todayFormatted = `${year}-${month}-${day}`;

    dateInput.setAttribute('min', todayFormatted);

    dateInput.addEventListener('input', function() {
        const selectedDate = new Date(this.value);
        const todayDate = new Date(todayFormatted);

        if (selectedDate < todayDate) {
            this.value = todayFormatted;
            // エラーメッセージを表示
            alert('過去の日付は選択できません。本日以降の日付を選択してください。');
        }
    });
});