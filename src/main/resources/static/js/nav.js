(function () {
  const user = JSON.parse(localStorage.getItem('user') || 'null');
  if (user) {
    document.getElementById('navAuth').innerHTML =
      '<a href="/profile" class="btn btn-outline-light btn-sm">' + user.nickname + '님</a>' +
      '<button class="btn btn-outline-light btn-sm" onclick="logout()">로그아웃</button>';
  }
})();

function logout() {
  localStorage.removeItem('user');
  location.href = '/';
}
