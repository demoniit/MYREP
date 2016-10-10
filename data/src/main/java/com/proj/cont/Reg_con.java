

package com.proj.cont;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.proj.model.BillingAddress;
import com.proj.model.Customer;
import com.proj.model.ShippingAddress;
import com.proj.serv.CustomerService;

import javax.validation.Valid;
import java.util.List;


@Controller
public class Reg_con {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/register")
    public String registerCustomer(Model model){
        Customer customer = new Customer();
        BillingAddress billingAddress = new BillingAddress();
        ShippingAddress shippingAddress = new ShippingAddress();
        customer.setBillingAddress(billingAddress);
        customer.setShippingAddress(shippingAddress);

        model.addAttribute("customer", customer);

        return "about";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerCustomerPost(@Valid @ModelAttribute("customer") Customer customer, BindingResult result, Model model){

        if(result.hasErrors()){
            return "about";
        }

        List<Customer> customerList = customerService.getAllCustomers();

        for (int i=0; i< customerList.size(); i++){
            if(customer.getCustomerEmail().equals(customerList.get(i).getCustomerEmail())){
                model.addAttribute("emailMsg", "Email already exists");

                return "about";
            }

            if(customer.getUsername().equals(customerList.get(i).getUsername())){
                model.addAttribute("usernameMsg", "Username already exists");

                return "about";
            }
        }

        customer.setEnabled(true);
        customerService.addCustomer(customer);
        return "registerCustomerSuccess";
    }

} // The End of Class;