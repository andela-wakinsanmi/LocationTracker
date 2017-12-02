package com.spykins.locationtracker.ui.registrationView;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.spykins.locationtracker.Util;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RegisterLocationViewModelTest {
    RegisterLocationViewModel mRegisterLocationViewModel;

    @Mock
    RegistrationContract.View mMockView;

    @Mock
    RegistrationContract.ViewModel mViewModel;

    Util mMockUtil;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mRegisterLocationViewModel = new RegisterLocationViewModel();
        mMockUtil = new Util();
        mRegisterLocationViewModel.setView(mMockView, mMockUtil);

    }


    @Test
    public void testShouldNotProceedWithRegisterationAndCallErrorForAddress() throws Exception {
        boolean result = mRegisterLocationViewModel.shouldProceedWithRegisteration("","","");
        verify(mMockView).setErrorTextViewForAddressText();
        assertTrue(!result);
    }

    @Test
    public void testShouldNotProceedWithRegisterationAndCallErrorForLocation() throws Exception {
        boolean result = mRegisterLocationViewModel.shouldProceedWithRegisteration("Amity","","");
        verify(mMockView, never()).setErrorTextViewForAddressText();
        verify(mMockView).setErrorTextViewForLocation();
        assertTrue(!result);
    }

    @Test
    public void shouldProceedWithRegisteration() throws Exception {
        boolean result = mRegisterLocationViewModel.shouldProceedWithRegisteration("Amity","23.098","54.9098");
        verify(mMockView, never()).setErrorTextViewForAddressText();
        verify(mMockView, never()).setErrorTextViewForLocation();
        assertTrue(result);
    }

}