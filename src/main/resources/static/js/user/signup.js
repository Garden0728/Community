const phoneRegex = /^01[016789]-?\d{3,4}-?\d{4}$/;

document.getElementById('passwordConfirm').addEventListener('input', function () {
  const pw = document.getElementById('password').value;
  const msg = document.getElementById('passwordConfirmMsg');
  if (!this.value) { msg.textContent = ''; return; }
  if (pw === this.value) {
    msg.className = 'form-text text-success';
    msg.textContent = '비밀번호가 일치합니다.';
  } else {
    msg.className = 'form-text text-danger';
    msg.textContent = '비밀번호가 일치하지 않습니다.';
  }
});

document.getElementById('loginId').addEventListener('blur', async function () {
  const loginId = this.value.trim();
  if (!loginId) return;
  const msg = document.getElementById('loginIdMsg');
  const dup = await fetch('/api/users/check/loginId?loginId=' + encodeURIComponent(loginId)).then(r => r.json());
  msg.className = dup ? 'form-text text-danger' : 'form-text text-success';
  msg.textContent = dup ? '이미 사용 중인 아이디입니다.' : '사용 가능한 아이디입니다.';
});

document.getElementById('nickname').addEventListener('blur', async function () {
  const nickname = this.value.trim();
  if (!nickname) return;
  const msg = document.getElementById('nicknameMsg');
  const dup = await fetch('/api/users/check/nickname?nickname=' + encodeURIComponent(nickname)).then(r => r.json());
  msg.className = dup ? 'form-text text-danger' : 'form-text text-success';
  msg.textContent = dup ? '이미 사용 중인 닉네임입니다.' : '사용 가능한 닉네임입니다.';
});

document.getElementById('phone').addEventListener('blur', async function () {
  const phone = this.value;
  const msg = document.getElementById('phoneMsg');
  if (!phoneRegex.test(phone)) {
    msg.className = 'form-text text-danger';
    msg.textContent = '올바른 전화번호 형식이 아닙니다. (예: 010-1234-5678)';
    return;
  }
  const dup = await fetch('/api/users/check/phone?phone=' + encodeURIComponent(phone)).then(r => r.json());
  msg.className = dup ? 'form-text text-danger' : 'form-text text-success';
  msg.textContent = dup ? '이미 등록된 전화번호입니다.' : '사용 가능한 전화번호입니다.';
});

async function doSignup() {
  const phone = document.getElementById('phone').value;
  if (!phoneRegex.test(phone)) { showError('올바른 전화번호 형식이 아닙니다.'); return; }
  const body = {
    loginId: document.getElementById('loginId').value,
    name: document.getElementById('name').value,
    nickname: document.getElementById('nickname').value,
    phone,
    password: document.getElementById('password').value,
    passwordConfirm: document.getElementById('passwordConfirm').value,
    gender: document.getElementById('gender').value
  };
  try {
    const res = await fetch('/api/users/create', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
    if (res.ok) {
      alert('회원가입이 완료되었습니다!');
      location.href = '/login';
    } else {
      const data = await res.json();
      showError(data.message || '회원가입에 실패했습니다.');
    }
  } catch (e) {
    showError('오류가 발생했습니다.');
  }
}

function showError(msg) {
  const el = document.getElementById('errorMsg');
  el.textContent = msg;
  el.classList.remove('d-none');
}
