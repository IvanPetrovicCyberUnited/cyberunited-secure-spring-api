const fetch = require('node-fetch');

module.exports.login = async function () {
  const res = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username: 'user', password: 'password' })
  });
  const data = await res.json();
  return data.token;
};
