package com.example.lock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.R.menu;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class BlueToothCActivity extends Activity {

	static Boolean Isconnect = Boolean.valueOf(false);
	public static final int MESSAGE_TOAST = 5;
	private static final int REQUEST_CONNECT_DEVICE = 1; // 宏定义查询设备句柄
	private static final int REQUEST_ENABLE_BT = 0;
	private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB"; // SPP服务UUID号
	public static final String TOAST = "toast";
	public static BluetoothDevice device;
	final static int MENU_BLUETOOTH = Menu.FIRST;
	final static int MENU_REFRESH = Menu.FIRST + 1;
	final static int MENU_QUIT = Menu.FIRST + 2;
	private EditText editText1;
	private TextView textView2;
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;
	private Button button9;
	private Button button0;
	private Button buttonBack;
	private Button buttonEnter;
	private Button buttonConnect;
	private Button buttonFresh;
	private byte[] byte1 = "ON1".getBytes();
	private byte[] byte2 = "ON2".getBytes();
	private byte[] byte3 = "ON3".getBytes();
	private byte[] byte4 = "ON4".getBytes();
	private byte[] byte5 = "ON5".getBytes();
	private byte[] byte6 = "ON6".getBytes();
	private byte[] byte7 = "ON7".getBytes();
	private byte[] byte8 = "ON8".getBytes();
	private byte[] byte9 = "ON9".getBytes();
	private byte[] byte0 = "ONE".getBytes();
	private byte[] byteBack = "ONA".getBytes();
	private byte[] byteEnter = "ONC".getBytes();
	// private byte[] bytedown = "ONB".getBytes();
	// private byte[] bytef = "ONF".getBytes();
	// private byte[] byteleft = "ONC".getBytes();
	// private byte[] byteright = "OND".getBytes();
	// private byte[] byteup = "ONA".getBytes();

	private InputStream is; // 输入流，用来接收蓝牙数据
	// private TextView text0; //提示栏解句柄
	private EditText edit0; // 发送数据输入句柄
	private TextView dis; // 接收数据显示句柄
	private ScrollView sv; // 翻页句柄
	private String smsg = ""; // 显示用数据缓存
	private String fmsg = ""; // 保存用数据缓存

	BluetoothDevice _device = null; // 蓝牙设备
	BluetoothSocket _socket = null; // 蓝牙通信socket
	boolean _discoveryFinished = false;
	boolean bRun = true;
	boolean bThread = false;

	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter(); // 获取本地蓝牙适配器，即蓝牙设备

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		editText1 = (EditText) findViewById(R.id.editText1);
		textView2 = (TextView) findViewById(R.id.textView2);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		button7 = (Button) findViewById(R.id.button7);
		button8 = (Button) findViewById(R.id.button8);
		button9 = (Button) findViewById(R.id.button9);
		button0 = (Button) findViewById(R.id.button0);
		buttonBack = (Button) findViewById(R.id.buttonBack);
		buttonEnter = (Button) findViewById(R.id.buttonEnter);
		buttonConnect = (Button) findViewById(R.id.buttonConnect);
		buttonFresh = (Button) findViewById(R.id.buttonFresh);

		// 如果打开本地蓝牙设备不成功，提示信息，结束程序
		if (_bluetooth == null) {
			Toast.makeText(this, "无法打开手机蓝牙，请确认手机是否有蓝牙功能！", Toast.LENGTH_LONG)
					.show();
			finish();
			return;
		}

		// 设置设备可以被搜索
		new Thread() {
			public void run() {
				if (_bluetooth.isEnabled() == false) {
					_bluetooth.enable();
				}
			}
		}.start();

		buttonFresh.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流
					String ran = "";
					for (int j = 0; j < 6; j++) {
						ran = ran + ""
								+ String.valueOf((int) (Math.random() * 9 + 0));
					}
					editText1.setText(ran);
					// byte[] bos = ("P" +
					// editText1.getText().toString()).getBytes();
					byte[] bos = ("P" + ran).getBytes();
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		button1.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byte1;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		button2.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byte2;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		button3.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byte3;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		button4.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byte4;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		button5.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byte5;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		button6.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byte6;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		button7.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byte7;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		button8.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byte8;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		button9.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byte9;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		button0.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byte0;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		buttonBack.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byteBack;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});

		buttonEnter.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = 0;
				int n = 0;
				try {
					OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流

					byte[] bos = byteEnter;
					for (i = 0; i < bos.length; i++) {
						if (bos[i] == 0x0a)
							n++;
					}
					byte[] bos_new = new byte[bos.length + n];
					n = 0;
					for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
						if (bos[i] == 0x0a) {
							bos_new[n] = 0x0d;
							n++;
							bos_new[n] = 0x0a;
						} else {
							bos_new[n] = bos[i];
						}
						n++;
					}

					os.write(bos_new);
				} catch (IOException e) {
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE: // 连接结果，由DeviceListActivity设置返回
			// 响应返回结果
			if (resultCode == Activity.RESULT_OK) { // 连接成功，由DeviceListActivity设置返回
				// MAC地址，由DeviceListActivity设置返回
				String address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// 得到蓝牙设备句柄
				_device = _bluetooth.getRemoteDevice(address);

				// 用服务号得到socket
				try {
					_socket = _device.createRfcommSocketToServiceRecord(UUID
							.fromString(MY_UUID));
				} catch (IOException e) {
					Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT).show();
				}
				// 连接socket
				// Button btn = (Button)
				// findViewById(R.id.button3);////////////////////////
				try {
					_socket.connect();
					Toast.makeText(this, "连接" + _device.getName() + "成功！",
							Toast.LENGTH_SHORT).show();
					buttonConnect.setText("断开");
				} catch (IOException e) {
					try {
						Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT)
								.show();
						_socket.close();
						_socket = null;
					} catch (IOException ee) {
						Toast.makeText(this, "连接失败！", Toast.LENGTH_SHORT)
								.show();
					}

					return;
				}

				// 打开接收线程
				try {
					is = _socket.getInputStream(); // 得到蓝牙数据输入流
				} catch (IOException e) {
					Toast.makeText(this, "接收数据失败！", Toast.LENGTH_SHORT).show();
					return;
				}
				if (bThread == false) {
					ReadThread.start();
					bThread = true;
				} else {
					bRun = true;
				}
			}
			break;
		default:
			break;
		}
	}

	// 接收数据线程
	Thread ReadThread = new Thread() {

		public void run() {
			int num = 0;
			byte[] buffer = new byte[1024];
			byte[] buffer_new = new byte[1024];
			int i = 0;
			int n = 0;
			bRun = true;
			// 接收线程
			while (true) {
				try {
					while (is.available() == 0) {
						while (bRun == false) {
						}
					}
					while (true) {
						num = is.read(buffer); // 读入数据
						n = 0;

						String s0 = new String(buffer, 0, num);
						fmsg += s0; // 保存收到数据
						for (i = 0; i < num; i++) {
							if ((buffer[i] == 0x0d) && (buffer[i + 1] == 0x0a)) {
								buffer_new[n] = 0x0a;
								i++;
							} else {
								buffer_new[n] = buffer[i];
							}
							n++;
						}
						String s = new String(buffer_new, 0, n);
						smsg += s; // 写入接收缓存
						if (is.available() == 0)
							break; // 短时间没有数据才跳出进行显示
					}
					// 发送显示消息，进行显示刷新
					handler.sendMessage(handler.obtainMessage());
				} catch (IOException e) {
				}
			}
		}

	};

	// 消息处理队列
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			dis.setText(smsg); // 显示数据
			sv.scrollTo(0, dis.getMeasuredHeight()); // 跳至数据最后一页
		}
	};

	// 关闭程序掉用处理部分
	public void onDestroy() {
		super.onDestroy();
		if (_socket != null) // 关闭连接socket
			try {
				_socket.close();
			} catch (IOException e) {
			}
		// _bluetooth.disable(); //关闭蓝牙服务
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, MENU_BLUETOOTH, 0, "连接蓝牙设备");
		menu.add(0, MENU_REFRESH, 1, "刷新动态密码");
		menu.add(0, MENU_QUIT, 2, "退出");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case MENU_BLUETOOTH:
			if (_bluetooth.isEnabled() == false) { // 如果蓝牙服务不可用则提示
				Toast.makeText(this, " 打开蓝牙中...", Toast.LENGTH_LONG).show();
				break;
			}

			// 如未连接设备则打开DeviceListActivity进行设备搜索
			// Button btn = (Button) findViewById(R.id.Button03);
			if (_socket == null) {
				Intent serverIntent = new Intent(this, DeviceListActivity.class); // 跳转程序设置
				startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE); // 设置返回宏定义
			} else {
				// 关闭连接socket
				try {

					is.close();
					_socket.close();
					_socket = null;
					bRun = false;
					buttonConnect.setText("连接");

				} catch (IOException e) {
				}
			}
			break;

		case MENU_REFRESH:
			int i = 0;
			int n = 0;
			try {
				OutputStream os = _socket.getOutputStream(); // 蓝牙连接输出流
				String ran = "";
				for (int j = 0; j < 6; j++) {
					ran = ran + ""
							+ String.valueOf((int) (Math.random() * 9 + 0));
				}
				editText1.setText(ran);
				byte[] bos = editText1.getText().toString().getBytes();
				for (i = 0; i < bos.length; i++) {
					if (bos[i] == 0x0a)
						n++;
				}
				byte[] bos_new = new byte[bos.length + n];
				n = 0;
				for (i = 0; i < bos.length; i++) { // 手机中换行为0a,将其改为0d 0a后再发送
					if (bos[i] == 0x0a) {
						bos_new[n] = 0x0d;
						n++;
						bos_new[n] = 0x0a;
					} else {
						bos_new[n] = bos[i];
					}
					n++;
				}

				os.write(bos_new);
			} catch (IOException e) {
			}
			break;
		case MENU_QUIT:
			finish();
			break;
		}
		return false;
	}

}
