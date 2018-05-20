package com.guc.mylibrary.widgets.contacts;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guc.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class UserAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<UserKH> users;
    private Dialog noticeDialog;
    private boolean isTxl = false;
    
    public UserAdapter(Context context) {
        this.mContext = context;
        users = new ArrayList<>();
    }

    public void setData(List<UserKH> data){
        this.users.clear();
        this.users.addAll(data);
    }



    @Override
    public int getCount() {
    	if (users != null) {
    		return users.size();
		}else {
			return 0;
		}
        
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.tvItem = (LinearLayout) convertView.findViewById(R.id.item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvName.setText(users.get(position).getDWMC());
        viewHolder.tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoticeDialog(users.get(position));
            }
        });
        //当前的item的title与上一个item的title不同的时候回显示title(A,B,C......)
        if(position == getFirstLetterPosition(position) && !users.get(position).letter.equals("@")){
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
            viewHolder.tvTitle.setText(users.get(position).letter.toUpperCase());
        }else {
            viewHolder.tvTitle.setVisibility(View.GONE);
        }


        return convertView;
    }

    /**
     * 顺序遍历所有元素．找到position对应的title是什么（A,B,C?）然后找这个title下的第一个item对应的position
     *
     * @param position
     * @return
     */
    private int getFirstLetterPosition(int position) {

        String letter = users.get(position).letter;
        int cnAscii = ChineseToEnglish.getCnAscii(letter.toUpperCase().charAt(0));
        int size = users.size();
        for (int i = 0; i < size; i++) {
            if(cnAscii == users.get(i).letter.charAt(0)){
                return i;
            }
        }
        return -1;
    }

    //通知按钮
    private void showNoticeDialog(final UserKH userKH){
        SpannableString spanString = new SpannableString(userKH.getDWMC()
                +"\r\n"+"联系人："+userKH.getLXRXM()
                +"\r\n"+"联系电话："+userKH.getSJHM());
        spanString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.colorOrange)) , 0, userKH.getDWMC().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanString.setSpan(new RelativeSizeSpan(1.2f) , 0, userKH.getDWMC().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("是否拨打");
        builder.setMessage(spanString)
        ;
        builder.setPositiveButton("拨打", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {try {
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+userKH.getSJHM()));
                mContext.startActivity(intent);
            }catch (SecurityException e){

            }
            }
        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.setCancelable(false);
        noticeDialog.show();
    }

    /**
     * 顺序遍历所有元素．找到letter下的第一个item对应的position
     * @param letter
     * @return
     */
    public int getFirstLetterPosition(String letter){
        int size = users.size();
        for (int i = 0; i < size; i++) {
            if(letter.charAt(0) == users.get(i).letter.charAt(0)){
                return i;
            }
        }
        return -1;
    }

    public boolean isTxl() {
		return isTxl;
	}

	public void setTxl(boolean isTxl) {
		this.isTxl = isTxl;
	}

	class ViewHolder {
        TextView tvName;
        TextView tvTitle;
        LinearLayout tvItem;
    }

}
