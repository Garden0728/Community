let verifiedUserId = null;

async function verify() {
  const loginId = document.getElementById('loginId').value.trim();
  const phone = document.getElementById('phone').value.trim();
  const res = await fetch('/api/users/verify-for-password', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ loginId, phone })
  });
  const data = await res.json();
  const msg = document.getElementById('verifyMsg');
  if (res.ok) {
    verifiedUserId = data.userId;
    document.getElementById('step1').style.display = 'none';
    document.getElementById('step2').style.display = 'block';
  } else {
    msg.className = 'alert alert-danger';
    msg.textContent = data.message;
    msg.classList.remove('d-none');
  }
}

async function resetPassword() {
  const newPassword = document.getElementById('newPassword').value;
  const newPasswordConfirm = document.getElementById('newPasswordConfirm').value;
  const res = await fetch('/api/users/' + verifiedUserId + '/reset-password', {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ newPassword, newPasswordConfirm })
  });
  const msg = document.getElementById('resetMsg');
  if (res.ok) {
    msg.className = 'alert alert-success';
    msg.textContent = '비밀번호가 변경되었습니다.';
    msg.classList.remove('d-none');
    setTimeout(() => location.href = '/login', 1500);
  } else {
    const data = await res.json();
    msg.className = 'alert alert-danger';
    msg.textContent = data.message;
    msg.classList.remove('d-none');
  }
}
