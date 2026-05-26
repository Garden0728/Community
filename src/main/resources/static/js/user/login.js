async function doLogin() {
  const loginId = document.getElementById('loginId').value;
  const password = document.getElementById('password').value;
  const res = await fetch('/api/users/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ loginId, password })
  });
  const data = await res.json();
  if (res.ok) {
    localStorage.setItem('user', JSON.stringify({ userId: data.userId, loginId: data.loginId, nickname: data.nickname }));
    location.href = '/';
  } else {
    const el = document.getElementById('errorMsg');
    el.textContent = data.message;
    el.classList.remove('d-none');
  }
}

document.addEventListener('keydown', e => { if (e.key === 'Enter') doLogin(); });
