const user = JSON.parse(localStorage.getItem('user') || 'null');
if (!user) { alert('로그인이 필요합니다.'); location.href = '/login'; }

async function changePassword() {
  const body = {
    currentPassword: document.getElementById('currentPassword').value,
    newPassword: document.getElementById('newPassword').value,
    newPasswordConfirm: document.getElementById('newPasswordConfirm').value
  };
  const res = await fetch('/api/users/' + user.userId + '/password', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body)
  });
  const msg = document.getElementById('pwMsg');
  if (res.ok) {
    msg.className = 'alert alert-success';
    msg.textContent = '비밀번호가 변경되었습니다.';
    msg.classList.remove('d-none');
    setTimeout(() => location.href = '/profile', 1000);
  } else {
    const data = await res.json();
    msg.className = 'alert alert-danger';
    msg.textContent = data.message;
    msg.classList.remove('d-none');
  }
}
