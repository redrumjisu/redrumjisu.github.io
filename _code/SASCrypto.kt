class SASCrypto(val NFilterPublicKey: String): AbstractCrypto() {

    val DEFAULT_PREFIX = "" // 
    val CoworkerCode = "".toByteArray() //
    val algorithm = "AES"

    var key: String? = null
    var NFilterPublicCode: ByteArray? = null
    var NFilterPrivateCode: ByteArray? = null

    init {
        NFilterPublicCode = KeyGenerator.getInstance().getNFilterPublicCode(NFilterPublicKey)
        NFilterPrivateCode = KeyGenerator.getInstance().getNFilterPrivateCode(NFilterPublicKey)
        key = KeyGenerator.getInstance().getAESKey(String(CoworkerCode), String(NFilterPublicCode), String(NFilterPrivateCode))
        setPrefix(DEFAULT_PREFIX)
    }

    override fun innerDecrypt(byteArray: ByteArray?): ByteArray = getCipher(Cipher.DECRYPT_MODE, byteArray)

    override fun innerEncrypt(byteArray: ByteArray?): ByteArray = getCipher(Cipher.ENCRYPT_MODE, byteArray)

    private fun getCipher(mode: Int, byteArray: ByteArray?): ByteArray {
        val secretKeySpec = SecretKeySpec(key?.toByteArray(), algorithm)
        val cipher: Cipher = Cipher.getInstance(algorithm)
        cipher.init(mode, secretKeySpec)
        return cipher.doFinal(byteArray)
    }
}
