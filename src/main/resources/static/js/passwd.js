document.getElementById('showPassword').addEventListener('change', function () {
    const passwordField = document.getElementById('password');
    // チェックボックスがチェックされている場合はパスワードを表示
    if (this.checked) {
        passwordField.setAttribute('type', 'text');
    } else {
        passwordField.setAttribute('type', 'password');
    }
});
