function authenticate(helper, params, credentials) {
  var msg = helper.prepareMessage();
  msg.getRequestHeader().setMethod('POST');
  msg.getRequestHeader().setURI(new org.apache.commons.httpclient.URI(params.get('loginUrl'), true));
  msg.setRequestBody('{"username":"' + credentials.getParam('username') + '","password":"' + credentials.getParam('password') + '"}');
  msg.getRequestHeader().setHeader('Content-Type','application/json');
  helper.sendAndReceive(msg);
  var token = JSON.parse(msg.getResponseBody().toString()).token;
  var result = new org.zaproxy.zap.authentication.AuthenticationResult(true);
  result.setBearerToken(token);
  return result;
}
