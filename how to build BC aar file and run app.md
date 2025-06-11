

# How to build BC aar file and run app

## 1. Build BCPP aar file.

###  Step-1. replace `gradle ` file

- Replace the [`build.gradle`](https://github.com/DspreadOrg/android-abecsdemo/blob/main/bcppE5855/build.gradle) file in the [bcppE5855 directory](https://github.com/DspreadOrg/android-abecsdemo/tree/main/bcppE5855) with the [`build.gradle`](https://github.com/DspreadOrg/android-abecsdemo/blob/main/bcppE5855/other/build.gradle) file in the [bcppE5855/other directory](https://github.com/DspreadOrg/android-abecsdemo/tree/main/bcppE5855/other),  and then synchronize gradle.



### Step-2. Unzip libPPComDsp.aar

- Execute the `upzipAarFile` task in the [`build.gradle`](https://github.com/DspreadOrg/android-abecsdemo/blob/main/bcppE5855/build.gradle) file of the bcppE5855 directory.

![](https://i.ooxx.ooo/i/OTU3N.png)



### Step-3. Build BCPP_E5855-Dspread aar file

- Execute the `buildBCPPAar` task in the [`build.gradle`](https://github.com/DspreadOrg/android-abecsdemo/blob/main/bcppE5855/build.gradle) file of the bcppE5855 directory

![](https://i.ooxx.ooo/i/ODc0N.png)


## 2. Build APP & run APP using BCPP aar.

- 1. Complete steps 1, 2, and 3 in build BCPP aar file.
- 2. Replace the [`build.gradle`](https://github.com/DspreadOrg/android-abecsdemo/blob/main/build.gradle) file in the [app directory](https://github.com/DspreadOrg/android-abecsdemo/tree/main/app) with the [`build.gradle`](https://github.com/DspreadOrg/android-abecsdemo/blob/main/app/other/build.gradle) file in the [app/other directory](https://github.com/DspreadOrg/android-abecsdemo/tree/main/app/other),  and then synchronize gradle.
- 3. Build and run APP. 

  

# How to Resume Building and Running Applications via BCPP Source Code 

- 1. Restore the build.gradle file in the bcppE5855 directory.
- 2. Restore the build.gradle file in the app directory
- 3. synchronize project gradle.
- 4. Build and run app.