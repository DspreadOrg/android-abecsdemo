# PPComLibrary

## Introduction
PPComLibrary is an Android library project for payment terminal integration based on the Abecs protocol. It provides comprehensive PIN pad communication and EMV transaction functionalities, enabling seamless integration with payment terminals and handling various payment processing tasks.

## Project Structure
```
ppcomlibrary/
├── app/                           # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/             # Source code
│   │   │   ├── res/              # Resources
│   │   │   └── AndroidManifest.xml
|   |──libs/
|   |   ├──BCPP_E5855-Dspread_release.aar
|   |   ├──bas-v1.0.0.aar
|   |   ├──cloudpossdkV1.5.4.33_Standard.aar #Abecs protocol inheritance library
|   |   ├──files-v1.0.3.aar
|   |   └──user-v1.0.1.aar
│   ├── build.gradle              # App module build config
│   └── proguard-rules.pro        # ProGuard rules
├── dsplib/                       # Core library modules
│   ├── abecslib/                 # ABECS library integration
│   └── ppcomlib/                 # PPCom core functionality
├── gradle/                       # Gradle wrapper files
├── build.gradle                  # Project build config
└── settings.gradle              # Project settings
```

## Features
### 1. PIN Pad Device Communication
- Device connection and initialization
- Status monitoring and event handling
- Secure communication protocols
- Real-time device status updates

### 2. EMV Transaction Processing
- Full EMV transaction flow support
- Chip card handling
- Contactless payment support
- Transaction data management

### 3. Card Operations
- Magnetic stripe card reading
- EMV chip card operations
- GetCard operation for retrieving card data
- GoOnChip for EMV processing
- FinishChip for transaction completion

### 4. Security Operations
- PIN encryption
- Secure key management
- Data encryption
- Secure communication channels

### 5. Table Management
- AID (Application Identifier) table management
- CAPK (Certification Authority Public Key) handling
- Dynamic table updates
- Configuration management

## Technical Requirements
- Minimum SDK Version: Android 21 (Lollipop)
- Target SDK Version: 33 (Android 13)
- Development Environment: Android Studio
- Build System: Gradle
- Java Version: Java 8
- AndroidX Support

## Required Permissions
```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
```

## Installation Steps
1. Clone the repository:
```bash
git clone [repository URL]
git submodule update --init --recursive
```

2. Open the project in Android Studio.
3. Sync Gradle files.
4. Build the project.

## Dependencies
### Core Dependencies
- AndroidX Core KTX - Core Android functionalities
- AndroidX Lifecycle - Component lifecycle management
- AndroidX Compose - Modern UI toolkit
- AndroidX AppCompat - Backward compatibility
- Material Design Components - UI components

## Usage Guide
### 1. Permission Handling
```java
ManageFilesPermissionHelper.requestPermission(activity, new OnManageAllFilesPermissionResult() {
    @Override
    public void onGranted() {
        // Permission granted, proceed with operations
    }

    @Override
    public void onDenied() {
        // Handle permission denial
    }
});
```

### 2. Library Initialization (Binding Service)
```java
PPCompAndroid.getInstance().initBindService();
```

### 3. Initialize Object
```java
AcessoFuncoesPinpad acessoFuncoes = GestaoBibliotecaPinpad.obtemInstanciaAcessoFuncoesPinpad();
```

### 4. Basic Operations
#### Device Connection
```java
// Open device connection
acessoFuncoes.open();

// Close device connection
acessoFuncoes.close();
```

#### Retrieve Device Information
```java
// Get device status
acessoFuncoes.getInfo();
```

#### Card Operations
```java
// Retrieve card information
acessoFuncoes.getCard();

// Process chip card
acessoFuncoes.goOnChip();

// Complete transaction
acessoFuncoes.finishChip();
```

#### Table Management
```java
// Load AID/CAPK tables
acessoFuncoes.tableLoad();
```

#### Data Encryption
```java
// Encrypt data
acessoFuncoes.encryptData();
```

#### Check Card Removal
```java
// Check if card is inserted
acessoFuncoes.removeCard();
```

#### Card Communication
```java
// Card communication
acessoFuncoes.chipDirect();
```

## Technical Support
For technical support or any questions, please contact:
- Email: zhanghaibiao@mail.dspread.com
