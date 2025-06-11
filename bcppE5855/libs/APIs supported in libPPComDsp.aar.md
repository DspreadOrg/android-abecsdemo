# APIs supported in DeviceAbecs class of libPPComDsp.aar.



| Method Name | Description | Parameters | Return Value |  |
| --- | --- | --- | --- | --- |
| `deviceInit()` | Initializes the device. | None | None |  |
| `deviceInit(String deviceName)` | Initializes the device with a specified device name. | `deviceName`: The name of the device to be set. | None |  |
| `ppInit(String paramString1, String paramString2)` | Initializes the PPCom instance. | `paramString1`: Not used in the current implementation.<br>`paramString2`: Not used in the current implementation. | 0->success<br>other->fail |  |
| `ppcompMsg(int paramInt, byte[] paramArrayIn, byte[] paramArrayOut)` | Handles some abecs commands, | `paramInt`: The command ID.<br>`paramArrayIn`: The input byte array for the command.<br>`paramArrayOut`: The output byte array to store the response. | 0->success<br/>other->fail |  |
| `abecsStartCmd(String paramString)` | Starts an ABECS command. Checks if the input command string is one of the valid commands (`GCX`, `GTK`, `GOX`, `FCX`, `EBX`) and sets the command ID if valid. | `paramString`: The command string. | 0->success<br/>other->fail |  |
| `abecsSetParam(int paramInt1, int paramInt2, String paramString)` | Sets the parameters for an ABECS command. Determines the parameter format based on the parameter ID, converts the input string to the appropriate byte array, and sets the parameter in the command parameter data buffer. | `paramInt1`: The parameter ID.<br>`paramInt2`: The length of the parameter value.<br>`paramString`: The parameter value as a string. | 0->success<br/>other->fail |  |
| `abecsGetResp(int paramInt, Object paramAbecsGetRespOutput)` | Retrieves the response data for a specific tag from the command response data. Parses the TLV data in the response and sets the `returnCode` and `respData` fields in the output object if the tag is found. | `paramInt`: The tag to search for in the response data.<br>`paramAbecsGetRespOutput`: The output object to store the response data and return code. | None |  |
| `abecsExecNBlk()` | Executes the ABECS command in non - blocking mode with a timeout of 300 milliseconds. Calls the `abecsExecCmd` method with the appropriate parameters. | None | 0->success<br/>other->fail |  |
| `abecsStartExec()` | Executes the ABECS command in blocking mode . Calls the `abecsExecCmd` method with the appropriate parameters. | None | 0->success<br/>other->fail |  |
| `abecsFinishExec(AbecsFinishExecOutput paramAbecsFinishExecOutput)` | Finishes the ABECS command execution and sets the return code and message notification in the output object. | `paramAbecsFinishExecOutput`: The output object to store the return code and message notification. | None |  |
| `ppcompCheckSerialization(byte[] paramArrayOfbyte)` | Checks the validity of the data to be sent via serial communication. Parses the input data according to the protocol, performs CRC check, sends the data, and waits for an ACK response. | `paramArrayOfbyte`: The byte array of data to be sent. | 0->success<br/>other->fail |  |
| `ppcompAbortSerializedCmd()` | Aborts the serialized command. Sets the cancel flag, sleeps for 100 milliseconds, and then clears the cancel flag. | None | 0->success<br/>other->fail |  |
| `ppcompExecSerialization(byte[] paramArrayOfbyte)` | Receive data from the serial port. | `paramArrayOfbyte`: The byte array to store the received data. | The length of the received data. |  |
| `getDukptKeyInfo(int keyIndex)` | get the DUKPT key information (KSN and key status) for a specified key index. Checks if the key is valid, gets the KSN if available, and returns a `Dukpt_Key_Info` object. | `keyIndex`: The index of the key. | A `Dukpt_Key_Info` object containing the KSN and key status. If the key is invalid, the KSN is `null` and the key status is `DeviceAbecsConstant.ABECS_RET_NOKEY`. |  |
| `getMkskKeyStatus(int keyIndex)` | get the status of the MKSK key for a specified key index. | `keyIndex`: The index of the key. | 0->success<br/>other->fail |  |
|                                                              |                                                              |                                                              |                                                              |                            |

- The package name of `DeviceAbecs` class is `com.dspread.dsplibrary`.
