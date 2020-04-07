package com.iessanvicente.springboot.backend.apirest.auth;

public class JwtConfig {
	public static final String SECRET_KEY = "Some.Secret.Key.12345";
	
	public static final String RSA_PRIVATE = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEpQIBAAKCAQEA13joDuctqAQ7k7dn1+RPnver2sQuxxHJKFX8hZjvvfeG9Xb7\r\n" + 
			"b2e9O0ovuYevEF6WwQVjXdoom3Ro3006ZuQXafkN6ZDexH3/aQafdMxXQViecMks\r\n" + 
			"ysy8LTZPz1AU88hCSvYUH4f4ABbqwh7vtgkohEQ7olMRtw5bVryD7CmtlTTwBwZu\r\n" + 
			"slynvGW+nVQ07lPirq3z9i2M6JNZJ3ZrVcrsPzFQ9wuLABg6fs4VAnTUxw36qyTY\r\n" + 
			"jg5vITl0IB4C3S8fhcXrb6SmMLn5BhFe/dLg8xUegCtQttLyvxmJsd4s3wOP9tTG\r\n" + 
			"HWyhH3uiZDGnbjOKz+yIgy5qYAdveKySh3gQnwIDAQABAoIBAQDTr0vEQGgBVc82\r\n" + 
			"Cwww9HMoiMfET9Jo7ldCkl9Y3OxDJCfphW/1feRRmEuUqPFbUqImlWWOsaINP2s8\r\n" + 
			"XgHPTayXBwAdA59nSSWgh8omFA5w+2AFDA8+Wa9Rd7cmw9Uhvw8p1+HjLFB/vgP6\r\n" + 
			"nCwCGbM9Zk9z5MpVnfwsFV5BqJyJJL+lmvC60W6TKMulvJn0ircctnYCUs+sxN8i\r\n" + 
			"6qlZw6f4rc3iw5A/lj03CGonKhOCLXNV8BYN5v6j1RvsuC5U9hqpNgU4xxCnCn2K\r\n" + 
			"2JUIbseCnvmoob39uQbx++hFYIf01n57TB3nnilTM49Y+GLDyyz3NIniFWjwUKOl\r\n" + 
			"6SS30qHxAoGBAPidHSY1cYFo0Balt57kzi6D+RO/RRngSR+jQvLDP+f66FntAzIT\r\n" + 
			"QQlN7qrwWcCkbBrILm1YT9xp0TZUbulIsN/Bym/nmuFAo1+OePUBlT5Z81eFt630\r\n" + 
			"EM2MHviCAHc2QrZKi2f0EIC+SfqNv71XDwRmekk8B7bia1JslQd+Lv2pAoGBAN3f\r\n" + 
			"umlqM9bANrExj+NAi2s1P7Oq1dln/fa8KEHjP9+udY74kFts7p20fYzhBzEMX9d7\r\n" + 
			"yyOg62SwpBIJGydZQXnnDn+Yg48V20QdgkB2ia6RjTW5xnFyk59cNx4seKTtJu1M\r\n" + 
			"vS2niFg/AxXnyioi64ltoIpT9gCDLpamRvuz4LkHAoGAS8Vmyuxi6bQOJ7jeG9lU\r\n" + 
			"Vaz3TuNuphJLdw8FKoQDiCpagn3QCYUAv1s3MHYMhMAYCpmUPNM7k8vUbaCSJRt4\r\n" + 
			"fNnNXwnRlHFk2YzMpK+U0uoLVlN0xdmq/VqFXewRREQPVQunLUmfdf2JAooNNCHC\r\n" + 
			"e+7XulZHa5/aYcaiLSRyIfECgYEAn463JfvkD7nnGwKDcXAHUy/WAcUHSPOKJZgm\r\n" + 
			"c0uTsa5qiEikt7rc1rjn2Roy68j0W6iA67xby+tj8jxuNJvxhFLvkD8DzTBwoMl/\r\n" + 
			"1A/q74lovYw983PsSZwtXxuoHyMU7W5UVbK3UI7wDx7Dug2myQv3ecLrlG7CYEBv\r\n" + 
			"p68yGp0CgYEAnBWFKb4PDcBdndGLNTxRJFoyb5SPuId8xNrvhN9kV1qaX4h6KqcI\r\n" + 
			"GIJbpDHrKoYDqp9vyH+uevh+25e/E6x+y3BkizqnEw2typrehonocBgi66mjJSkW\r\n" + 
			"/HoxItxg3Pw3C4Dl5+fhr+3Vt59x5qooNZ0SC74ePdFFKeLx+ZpYvZ8=\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLIC = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA13joDuctqAQ7k7dn1+RP\r\n" + 
			"nver2sQuxxHJKFX8hZjvvfeG9Xb7b2e9O0ovuYevEF6WwQVjXdoom3Ro3006ZuQX\r\n" + 
			"afkN6ZDexH3/aQafdMxXQViecMksysy8LTZPz1AU88hCSvYUH4f4ABbqwh7vtgko\r\n" + 
			"hEQ7olMRtw5bVryD7CmtlTTwBwZuslynvGW+nVQ07lPirq3z9i2M6JNZJ3ZrVcrs\r\n" + 
			"PzFQ9wuLABg6fs4VAnTUxw36qyTYjg5vITl0IB4C3S8fhcXrb6SmMLn5BhFe/dLg\r\n" + 
			"8xUegCtQttLyvxmJsd4s3wOP9tTGHWyhH3uiZDGnbjOKz+yIgy5qYAdveKySh3gQ\r\n" + 
			"nwIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";
	
}
