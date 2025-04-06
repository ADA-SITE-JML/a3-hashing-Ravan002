## Option Chosen: **Option B**


## External Library Used for Hashing

This project uses the **xxHash** hashing algorithm, provided by the (https://central.sonatype.com/artifact/net.openhft/zero-allocation-hashing) library. The `LongHashFunction.xx()` method from this library is used to hash the input strings.
## How Overflow Works

### HashMap for Overflow Tracking

The program uses a **HashMap** to keep track of the current bucket index and the **latest overflow number** for each bucket. The key-value pair in the HashMap is as follows:

- **Key**: `bucket index` (e.g., `1`, `2`, etc.)
- **Value**: `last overflow number` (e.g., `0`, `1`, `2`, etc.)

For example, if we have the following files:
- `1.txt` (bucket file),
- `1_1.txt` (first overflow file for 1.txt bucket file),
- `1_2.txt` (second overflow file for 1.txt bucket file),

The HashMap will be for bucket 1:
- `{1->2}` Because last overflow file number is 2.


This means the bucket index `1` currently has two overflow files: `1.txt`, `1_1.txt`, and `1_2.txt`.

If there is no overflow file for bucket index 1 HashMapp will be:
- `{1->0}`

### Overflow Process

1. **Initial File Creation**:
   - When a new bucket is processed, the first file is created in the format `<bucketIndex>.txt`. For example, `1.txt` for bucket `1`.
   - If the file is full, overflow files are created in the format `<bucketIndex>_<overflowNumber>.txt`. For example, `1_1.txt`, `1_2.txt`, etc.

2. **Overflow Condition**:
   - When a new string is added to a bucket, the program checks if the string fits into the current file. If it does, it is added to the current file.
   - If the string exceeds the current file's size limit, the program creates a new overflow file (e.g., `1_3.txt`) and updates the HashMap.

3. **Updating the HashMap**:
   - Each time a new overflow file is created, the program updates the **overflow number** for the bucket in the HashMap.
   - For example, after the `1_2.txt` file overflows, a new file `1_3.txt` will be created, and the HashMap will be updated to:
     ```
     {1 -> 3}
     ```


## Input Constraints and Error Handling
- If the **bucket size is less than 2**, the program will terminate with an error.
- If the **input string length exceeds the bucket size**, the program will reject it. Otherwise, it would cause infinite overflow creation since no bucket (or overflow bucket) can hold it.

## Running the Application

### Prerequisites

Before running the application, you need to have the following:
1. **Java Development Kit (JDK)** installed on your machine.

### Step-by-Step Instructions

1. **Clone the Repository**:
First, clone the repository to your local machine:
  ```
  git clone https://github.com/ADA-SITE-JML/a3-hashing-Ravan002.git
  ```

2. **Navigate to the Project Directory**:
Go to the `hash_app` directory:
  ```
  cd a3-hashing-Ravan002/hash_app
  ```

3. **Compile the Java Files**:
To compile the Java file and external library to use for hashing, use the following commands:

- On **macOS** or **Linux**:
  ```
  javac -cp ".:lib/*" -d out src/HashApp.java
  ```

- On **Windows**:
  ```
  javac -cp ".;lib/*" -d out src/HashApp.java
  ```

4. **Run the Application**:
After compiling, you can run the application with the following command:

- On **macOS** or **Linux**:
  ```
  java -cp ".:lib/*:out" HashApp <number of buckets> <size of bucket>
  ```

- On **Windows**:
  ```
  java -cp ".;lib/*;out" HashApp <number of buckets> <size of bucket>
  ```

Replace `<number of buckets>` and `<size of bucket>` with appropriate values for your use case.

---
